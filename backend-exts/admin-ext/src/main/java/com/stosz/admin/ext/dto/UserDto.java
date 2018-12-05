package com.stosz.admin.ext.dto;

import com.stosz.admin.ext.enums.JobAuthorityRelEnum;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable{
    private Integer id;
    private String lastName;
    private String loginid;


    private List<JobDto> jobs;


    private JobAuthorityRelEnum jobAuthorityRelEnum = JobAuthorityRelEnum.myself;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public List<JobDto> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDto> jobs) {
        this.jobs = jobs;
    }

    public JobAuthorityRelEnum getJobAuthorityRelEnum() {
        return jobAuthorityRelEnum;
    }

    public void setJobAuthorityRelEnum(JobAuthorityRelEnum jobAuthorityRelEnum) {
        this.jobAuthorityRelEnum = jobAuthorityRelEnum;
    }
}
