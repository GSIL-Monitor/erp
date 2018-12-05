package com.stosz.admin.service.sync;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.service.admin.DepartmentService;
import com.stosz.olderp.ext.model.OldErpDepartment;
import com.stosz.olderp.ext.service.IOldErpDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/8]
 */
@Service
@EnableTransactionManagement
public class OldErpDepartmentSyncService {

    @Resource
    private DepartmentService departmentService;
    @Resource
    private OldErpTempDepartmentService oldErpTempDepartmentService;
    @Resource
    private IOldErpDepartmentService oldErpDepartmentService;


    private final Logger logger = LoggerFactory.getLogger(getClass());


    // 获取到所有的部门信息，根据部门负责人id去admin.user 表查询对应的department，与其对应，对应不上的存临时表
    public Future<Object> sync() {
        //先截断临时表
        oldErpTempDepartmentService.truncate();
        //使用OA和老erp部门的名称进行绑定
        List<OldErpDepartment> oldErpDepartments = pullByName();
        //将没有比对上的部门，用部门主管进行比对。
        pullByUser(oldErpDepartments);
        //绑定完成后，将OA存在但是老erp不存在的部门插入到老erp中，同时回填oldId;
        List<Department> departmentList = departmentService.findByNoOldId();
        for (Department department : departmentList) {
            insert(department);
        }
        return new AsyncResult<>("同步老erp部门信息完成！");
    }



    @Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void pullByUser(List<OldErpDepartment> oldErpDepartments) {
        if (oldErpDepartments == null || oldErpDepartments.isEmpty()) {
            logger.info("没有需要根据主管信息绑定的部门信息！");
            return;
        }
        for (OldErpDepartment oldErpDepartment : oldErpDepartments) {
            Integer id = oldErpDepartment.getIdUsers();
            Department department = null;
            try {
                department = departmentService.getByUserOldId(id);
            }catch(Exception e){
                Assert.isTrue(false, "根据部门主管ID["+id+"]进行部门对比的时候发生异常,请排查!");
            }
            if (department != null && department.getOldId() == null) {
                department.setOldId(oldErpDepartment.getIdDepartment());
                departmentService.updateOldId(department);
                logger.info("同步老erp部门信息成功，将老erp部门{} 与 新erp部门{}绑定！", oldErpDepartment, department);
            } else {
                oldErpTempDepartmentService.insert(oldErpDepartment);
                logger.info("同步老erp 部门{}时，找不到与部门主管id在新erp用户表中的对应部门id！");
            }
        }

    }

    @Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<OldErpDepartment> pullByName() {
        List<OldErpDepartment> oldErpDepartments = oldErpDepartmentService.findByNoRepeat();
        List<OldErpDepartment> oldErpDepartmentTemp = new ArrayList<>();
        Assert.notNull(oldErpDepartments, "查询老erp部门信息失败，返回null！");
        Assert.notEmpty(oldErpDepartments, "查询老erp部门信息失败，返回空集合！");
        for (OldErpDepartment oldErpDepartment : oldErpDepartments) {
            String oldName = oldErpDepartment.getTitle();
            if (oldName != null && oldName.contains("（")) {
                oldName = oldName.substring(0, oldName.indexOf("（"));
            }
            Department department = new Department();
            department.setOldId(oldErpDepartment.getId());
            department.setDepartmentName(oldName);
            Integer i = departmentService.updateOldIdByOldName(department);
            if (i != 1) {
                oldErpDepartmentTemp.add(oldErpDepartment);
            }
        }
        List<OldErpDepartment> oldErpDepartmentRepeatList = oldErpDepartmentService.findByRepeat();
        for (OldErpDepartment oldErpDepartment : oldErpDepartmentRepeatList) {
            String oldName = oldErpDepartment.getTitle();
            Department parentDepartment = null;
            try{
                parentDepartment = departmentService.getByOldId(oldErpDepartment.getParentId());
            }catch(Exception e){
                Assert.isTrue(false, "新erp根据绑定的老erpID查询时发生错误,新erp绑定的老erpID为:["+oldErpDepartment.getParentId()+"],错误为: ["+e.getMessage()+"] " );
            }
            Integer parentId = 0;
            if (parentDepartment != null) {
                parentId = parentDepartment.getId();
            }
            if (oldName != null && oldName.contains("（")) {
                oldName = oldName.substring(0, oldName.indexOf("（"));
            }
            Department department = new Department();
            department.setParentId(parentId);
            department.setOldId(oldErpDepartment.getId());
            department.setDepartmentName(oldName);
            Integer i = departmentService.updateOldIdByOldName(department);
            if (i != 1) {
                oldErpDepartmentTemp.add(oldErpDepartment);
            }
        }
        return oldErpDepartmentTemp;
    }


    /**
     * 添加部门信息，判断老erp是否存在部门信息，拿到老erp的部门id
     */
    private void insert(Department oaDepartment) {
        Integer oldId = oldErpDepartmentService.insertOldErpDepartment(oaDepartment);
        Assert.notNull(oldId, "插入老erp部门信息 返回的部门id为空");
        oaDepartment.setOldId(oldId);
        try {
            departmentService.updateOldId(oaDepartment);
        } catch (Exception e) {
            logger.info("OA的部门，插入新erp失败，部门信息{}", oaDepartment, e);
            oldErpDepartmentService.deleteOldErpDepartment(oldId);
        }
    }

}  
