package com.stosz.admin.ext.service;

import com.stosz.admin.ext.model.User;

import java.util.List;
import java.util.Set;

public interface IUserService {

    String url ="/admin/remote/IUserService";

    void insert(User user);

    void update(User user);

    void updateOldId(User user);

    User getByLastname(String lastname);

    List<User> findByIds(Set<Integer> idsets);

    List<User> findByCondition(String condition);
    List<User> findAll();

    User getById(Integer userId);
    
    User getByUserId(Integer id);

    User getByLoginid(String loginid);

    List<User> findBylastname(String lastname);

    List<User> findByDepartmentNos(List<String> departmentNos , String q);

    List<User> findByNoOldId();

    User getByOldId(Integer oldId);

    void truncate();

    void insertList(List<User> userList);

}
