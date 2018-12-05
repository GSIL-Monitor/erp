package com.stosz.admin.service.olderp;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.mapper.olderp.OldErpDepartmentMapper;
import com.stosz.olderp.ext.model.OldErpDepartment;
import com.stosz.olderp.ext.service.IOldErpDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by XCY on 2017/8/22.
 * desc: 老erp部门的service
 */
@Service
public class OldErpDepartmentService implements IOldErpDepartmentService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OldErpDepartmentMapper oldErpDepartmentMapper;



    @Resource
    private IDepartmentService departmentService;


    public Integer insertOldErpDepartment(Department oaDepartment) {
        Integer oaDepartmentParentId = oaDepartment.getParentId();
        String oaDepartmentTitle = oaDepartment.getDepartmentName();

        ///////////按照oa的部门id的父id去admin的department中查找老erp的部门id;如果查出来了就替换，否则==0
        Integer oldErpDepartmentId = departmentService.getOldIdById(oaDepartmentParentId);
        oldErpDepartmentId = oldErpDepartmentId == null ? 0 : oldErpDepartmentId;

        OldErpDepartment oldErpDepartment = new OldErpDepartment();
        oldErpDepartment.setParentId(oldErpDepartmentId);
        oldErpDepartment.setTitle(oaDepartmentTitle);

        Integer i = oldErpDepartmentMapper.insert(oldErpDepartment);//这个肯定是空的；todo
        Assert.isTrue(i == 1, "新建老erp部门信息失败！");
        return oldErpDepartment.getIdDepartment();

    }

    public void deleteOldErpDepartment(Integer id) {
        oldErpDepartmentMapper.delete(id);
    }

    @Override
    public List<OldErpDepartment> findByNoRepeat() {
        return oldErpDepartmentMapper.findAll();
    }

    @Override
    public List<OldErpDepartment> findByRepeat() {
        return oldErpDepartmentMapper.findByRepeat();
    }


}
