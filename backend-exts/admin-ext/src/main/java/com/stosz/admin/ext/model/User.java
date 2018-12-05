package com.stosz.admin.ext.model;

import com.stosz.admin.ext.enums.UserState;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class User extends AbstractParamEntity implements Serializable, ITable {

    @DBColumn
    private Integer id;
    /**
     * 登录账号
     */

    @DBColumn
    private String loginid;
    /**
     * 用户名
     */
    @DBColumn
    private String lastname;
    /**
     * 密码
     */
    private String password;
    /**
     * 生日
     */
    private Date birthday;

    private String sex;//默认为0；0：男，1：女

    /**
     * 电话号码
     */
    @DBColumn
    private String mobile;

    /**
     * 部门id
     */
    @DBColumn
    private Integer departmentId;
    /**
     * 主管id
     */
    @DBColumn
    private Integer managerId;
    /**
     * 用户拼音缩写
     */
    @DBColumn
    private String ecologyPinyinSearch;
    /**
     * 用户状态
     */
    @DBColumn
    private UserState state;
    /**
     * 邮箱
     */
    @DBColumn
    private String email;
    /**
     * 公司ID
     */
    @DBColumn
    private Integer companyId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 用户拥有的职位，用户-职位 多对多的关系
     */
    private List<Job> jobs;

    //    @DBColumn
    private Integer oldId;//老erp用户的id

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

    private Integer jobId;//oa那边职位id;

    private String departmentName;

    private String departmentNo;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid == null ? null : loginid.trim();
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname == null ? null : lastname.trim();
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getEcologyPinyinSearch() {
        return ecologyPinyinSearch;
    }

    public void setEcologyPinyinSearch(String ecologyPinyinSearch) {
        this.ecologyPinyinSearch = ecologyPinyinSearch == null ? null : ecologyPinyinSearch.trim();
    }

    public String getName(){
        return lastname;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public String getStateName() {
        return state==null?"":state.display();
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getTable() {
        return "user";
    }
}