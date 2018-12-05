package com.stosz.admin.service.sync;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.mapper.oa.OaDepartmentMapper;
import com.stosz.admin.service.admin.DepartmentService;
import com.stosz.olderp.ext.service.IOldErpDepartmentService;
import com.stosz.plat.common.StoszCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
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
import java.util.stream.Collectors;

/**
 * Created by XCY on 2017/8/18.
 * desc: oa 部门同步
 */
@Service
@EnableTransactionManagement
public class OaDepartmentSyncService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OaDepartmentMapper oaDepartmentMapper;
    @Resource
    private DepartmentService departmentService;

    @Resource
    private IOldErpDepartmentService oldErpDepartmentService;

    @Resource
    private OldErpDepartmentSyncService oldErpDepartmentSyncService;

    /**
     * 导入所有的OA部门信息
     */
    @Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pullAll() {
        List<Department> departmentList = oaDepartmentMapper.findAll();
        Assert.notNull(departmentList, "导入OA部门信息失败，获取到的OA部门集合为null");
        Assert.notEmpty(departmentList, "导入OA部门信息失败，获取到的OA部门集合为空");
        departmentService.truncate();
        departmentService.insertList(departmentList);
        logger.info("导入OA部门信息{}成功！", departmentList);
        return new AsyncResult<>("导入OA部门信息成功！");
    }

    /**
     * 导入所有的OA部门信息(页面按钮的触发)
     */
    @Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void pull() {
        List<Department> departmentList = oaDepartmentMapper.findAll();
        Assert.notNull(departmentList, "导入OA部门信息失败，获取到的OA部门集合为null");
        Assert.notEmpty(departmentList, "导入OA部门信息失败，获取到的OA部门集合为空");
        List<Department> resultDeptList = departmentService.findNo(departmentList);
        for (Department department: resultDeptList) {
            Integer departmentId = department.getId();
            Department departmentDB = departmentService.get(departmentId);
            if(departmentDB == null){
                departmentService.insert(department);
            }else{
                departmentService.update(department);
            }
        }
        logger.info("导入OA部门信息{}成功！", departmentList);
//        return new AsyncResult<>("导入OA部门信息成功！");
    }

}
