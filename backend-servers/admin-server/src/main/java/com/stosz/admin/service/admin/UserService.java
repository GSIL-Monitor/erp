package com.stosz.admin.service.admin;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.admin.mapper.admin.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author XCY
 * @version 2017/8/18
 */
@Service
public class UserService implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JobAuthorityRelService jobAuthorityRelService;
    @Resource
    private DepartmentService departmentService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public User getByOldId(Integer oldId) {
        return userMapper.getByOldId(oldId);
    }

    /**
     * 批量插入用户
     */
    public void insertList(List<User> userList) {
        Assert.notNull(userList, "批量插入用户的集合不允许为空！");
        Integer i = userMapper.insertBatch(userList, 0);
        Assert.isTrue(i > 0, "批量插入用户失败!");
        logger.info("批量插入用户成功，用户列表{}", userList);
    }

    /**
     * 插入单条用户
     */
    public void insert(User user) {
        Integer i = userMapper.insert(user);
        Assert.isTrue(i == 1, "插入新用户失败！");
        logger.info("插入新用户{}成功！", user);
    }

    /**
     * 更新用户的oa信息
     */
    public void update(User user) {
        Integer i = userMapper.update(user);
        Assert.isTrue(i == 1, "更新用户失败！");
        logger.info("更新用户{}成功！", user);
    }

    /**
     * 更新用户的老erpId
     */
    public void updateOldId(User user) {
        Integer i = userMapper.updateOldId(user);
        Assert.isTrue(i == 1, "更新用户失败！");
        logger.info("更新用户{}成功！", user);
    }

    @Override
    public User getByLastname(String lastname) {
        Assert.isTrue(lastname != null && !"".equals(lastname), "用户名不允许为空！");
        return userMapper.getByLastname(lastname);
    }

    /**
     * 根据登录账号获取到用户信息，包括部门，职位
     */
    public User getByLoginid(String loginid) {
        Assert.isTrue(loginid != null && !"".equals(loginid), "用户登录账号不允许为空！");
        return userMapper.getByLoginid(loginid);
    }


    /**
     * 根据集合获取uselist
     */
    public List<User> selectUserByIds(Set<Integer> idsets) {
        return userMapper.findByIds(idsets);
    }


    /**
     * 根据用户中文名获取对应用户
     */
    public List<User> findBylastname(String lastname) {
        return userMapper.findByLastname(lastname);
    }

    /**
     *
     * @param departmentNos 在规定的部门范围内
     * @param q  模糊查询
     * @return
     */
    @Override
    public List<User> findByDepartmentNos(List<String> departmentNos, String q) {
        if (departmentNos == null || departmentNos.isEmpty()) {
            return new ArrayList<>();
        }
        return userMapper.findByDepartmentNos(departmentNos, q);

    }

    @Override
    public List<User> findByNoOldId() {
        return userMapper.findByNoOldId();
    }

    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    /**
     * 根据用户id获取到对应老erp的用户id
     */
    public Integer getOldIdById(Integer id) {
        return userMapper.getOldIdById(id);
    }


    /**
     * 用户自动补全(登录名,loginid,lastname)
     */
    public List<User> findByCondition(String condition) {
        return userMapper.findByCondition(condition);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }


    /**
     * 根据id集合获取到所有匹配的用户
     */
    @Override
    public List<User> findByIds(Set<Integer> idsets) {
        return userMapper.findByIds(idsets);
    }

    /**
     * 截断表，目前仅限同步数据时，使用，其余地方慎用！！！
     */
    public void truncate() {
        userMapper.truncate();
    }

	@Override
    public User getByUserId(Integer id) {
		return userMapper.getByUserId(id);
    }

}
