package com.stosz.admin.service.admin;

import com.stosz.admin.ext.common.DepartmentNode;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.mapper.admin.DepartmentMapper;
import com.stosz.plat.common.StoszCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService implements IDepartmentService {


    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 批量插入部门列表
     */
    public void insertList(List<Department> departmentList) {
        Integer i = departmentMapper.insertList(0, departmentList);
        Assert.isTrue(i >= 1, "批量插入部门失败！");
        logger.info("批量插入部门信息成功，部门列表{}", departmentList);

    }

    /**
     * 插入部门信息
     */
    public void insert(Department department) {
        Integer i = departmentMapper.insert(department);
        Assert.isTrue(i == 1, "插入部门信息失败！");
        logger.info("插入部门信息成功，部门{}", department);
    }

    public Integer updateOldIdByOldName(Department department) {
        Assert.notNull(department, "修改的部门不允许为空！");
        Integer i = departmentMapper.updateOldIdByOldName(department);
        return i;
    }

    public List<Department> findByNoOldId() {
        return departmentMapper.findByNoOldId();
    }

    @Override
    public List<Department> findByParentId(Integer parentId) {
        Assert.notNull(parentId,"parentId不能为空");
        return departmentMapper.findByParentId(parentId);
    }

    @Override
    public Department findParentByDeptId(Integer deptId) {
        Department department = get(deptId);
        Assert.notNull(department,"要查询的部门在数据库中不存在！");
        String deptNO = department.getDepartmentNo();
        Assert.hasLength(deptNO,"该部门没有部门编号，不合法！");
        //截取到一级部门的部门编号
        String parentNo = deptNO.substring(0,2);
        return departmentMapper.getByNo(parentNo);
    }

    public void truncate() {
        departmentMapper.truncate();
        logger.info("截断部门表成功！");
    }

    public List<Department> getTopDepartment(){
        return departmentMapper.getTopDepartment();
    }

    /**
     * 更新部门
     */
    public void update(Department department) {
        Integer i = departmentMapper.update(department);
        Assert.isTrue(i == 1, "更新部门失败！");
        logger.info("更新部门{}信息成功！", department);
    }

    public void updateOldId(Department department) {
        Integer i = departmentMapper.updateOldId(department);
        Assert.isTrue(i == 1, "更新部门老erp的id失败！");
        logger.info("更新部门{}的oldId为{}成功!", department, department.getOldId());
    }

    /**
     * 根据部门id获取部门信息
     */
    @Override
    public Department get(Integer id) {
        Assert.notNull(id, "部门Id不能为空!");
        return departmentMapper.getById(id);
    }


    /**
     * 获取所有的部门列表
     */
    @Override
    public List<Department> findAll() {

        return departmentMapper.selectAllDepartment();
    }


    /**
     * 获取到所有部门的组织结构
     */
    @Override
    public DepartmentNode getNode() {
        List<Department> departments = departmentMapper.selectAllDepartment();
        Assert.notNull(departments, "获取部门列表为空！");
        return buildDepartmentTree(departments);
    }


    /**
     * 根据部门编号获取到该部门下所有的部门（包括该部门）
     */
    @Override
    public List<Department> findByNo(String no) {
        return departmentMapper.getDepartmentByNo(no);
    }

    /**
     * 根据部门编号获取到该部门的子树（包括该部门）
     */
    @Override
    public DepartmentNode getNodeByNo(String no) {
        List<Department> list = findByNo(no);
        Assert.notNull(list, "根据标号获取部门信息失败！");
        return buildDepartmentTree(list);
    }

    /**
     * 生成部门组织结构（树形）
     */
    public DepartmentNode buildDepartmentTree(List<Department> departments) {
        DepartmentNode root = DepartmentNode.createRootNode();
        buildDepartmentTree(departments, root);
        return root;
    }



    /**
     * 根据id获取 老erp部门id
     */
    @Override
    public Integer getOldIdById(Integer id) {
        return departmentMapper.getOldIdById(id);
    }

    @Override
    public Department getByOldId(Integer oldId) {
        return departmentMapper.getByOldId(oldId);
    }



    /**
     * 根据老erp用户id获取到匹配的部门
     */
    @Override
    public Department getByUserOldId(Integer oldId) {
        return departmentMapper.getByUserOldId(oldId);
    }

    /**
     * 生成部门树
     */
    private void buildDepartmentTree(List<Department> departments, DepartmentNode root) {
        if (departments == null || departments.isEmpty()) {
            return;
        }
        departments.stream().filter(e -> e.getParentId().equals(root.getId())).forEach(e -> root.addChild(convertToNode(e)));
        if (root.getLeaf()) {
            return;
        }
        for (DepartmentNode node : root.getChildren()) {
            buildDepartmentTree(departments, node);
        }
    }

    private DepartmentNode convertToNode(Department department) {
        DepartmentNode node = new DepartmentNode();
        node.setId(department.getId());
        node.setName(department.getDepartmentName());
        node.setNo(department.getDepartmentNo());
        node.setParentId(department.getParentId());
        node.setTlevel(department.getTlevel());
        node.setMasterId(department.getMasterId());
        node.setMasterName(department.getMasterName());
        node.setCreate_at(department.getCreateAt());
        return node;
    }


    /**
     * 算出传入的department的所有No
     */
    public List<Department> findNo(List<Department> list) {
        List<Department> result = new ArrayList<>();
        Department department = new Department();
        try {
            getDepartmentNo(list, department, result);
        } catch (Exception e) {
            logger.error("获取部门的NO 出错，集合：{}", list, e);
            throw new RuntimeException("获取部门的NO 出错");
        }
        return result;
    }

    /**
     * 获取部门编号
     */
    private void getDepartmentNo(List<Department> departmentList, Department department, List<Department> root) {
        Integer id = department.getId() == null ? 0 : department.getId();
        String parentNo = department.getDepartmentNo() == null ? "" : department.getDepartmentNo();
        List<String> noList = new ArrayList<>();
        noList.add(parentNo + "00");
        if (departmentList == null || departmentList.isEmpty()) {
            return;
        }
        List<Department> oneLevel = departmentList.stream().filter(e -> e.getParentId().equals(id)).collect(Collectors.toList());
        if (oneLevel == null || oneLevel.isEmpty()) {
            return;
        }
        for (Department oneDepartment : oneLevel) {
            //TODO 修改以后的结果
            String no = StoszCoder.toHex(noList, parentNo);
            oneDepartment.setDepartmentNo(no);
            noList.add(no);
            List<Department> secondLevel = departmentList.stream().filter(e -> e.getParentId().equals(oneDepartment.getId())).collect(Collectors.toList());
            if (!secondLevel.isEmpty()) {
                getDepartmentNo(departmentList, oneDepartment, root);
            }
        }
        root.addAll(oneLevel);

    }

	@Override
    public List<Department> findByIds(List<Integer> ids) {
		Assert.isTrue(ids != null && ids.size() > 0, "传入的部门id为空,无法查询部门信息!");
		return departmentMapper.findByIds(ids);
    }

    @Override
    public List<Department> findByNos(List<String> nos) {
        Assert.isTrue(nos != null && nos.size() > 0, "传入的部门编码[no]为空,无法查询部门信息!");
        return departmentMapper.findByNos(nos);
    }

    @Override
    public List<Department> findAllTopDept() {
        return departmentMapper.findAllTopDept();
    }

    @Override
    public DepartmentNode getNodeByDeptList(List<Department> departmentList) {
        List<Department> resultList = departmentMapper.findByDeptList(departmentList);
        return buildDepartmentTree(resultList);
    }

    @Override
    public List<Department> findByBuDeptList(List<Department> buDeptList) {
        return departmentMapper.findByDeptList(buDeptList);
    }
}
