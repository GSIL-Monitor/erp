package com.stosz.product.ext.model;


import java.time.LocalDateTime;

//排重报表实体类
public class ExcludeRepeat {

    private Integer checkerId;
    private String checker;                     //排重人员
    private Integer DepartmentId;
    private String departmentName;              //部门名称
    private Integer number;                     //每个部门查重总数
    private Integer checkOk;                    //通过的总数
    private Integer unCheckOk;                  //未通过
    private String adoptRate;                   //通过率
    private String UnAdoptRate;                 //未通过率
    private LocalDateTime stateTime;            //日期
    private Integer checkWarn;                   //有风险
    private Integer checkWarnCheckOk;           //有风险通过
    private Integer duplication;                //重复
    private Integer duplicationCheckOk;          //重复通过
    private Integer cancel;                     //拒接
    private Integer draft;                      //草稿(驳回)
    private Integer refuse;                     // 拒绝(排重有风险,主管取消)
    private Integer deptNumber;
    //参数
    private LocalDateTime minCreateAt;
    private LocalDateTime maxCreateAt;
    private String fsmName;
    private String dstState;
    private String otherState;
    private String optUid;

    public Integer getUnCheckOk() {
        return unCheckOk;
    }

    public void setUnCheckOk(Integer unCheckOk) {
        this.unCheckOk = unCheckOk;
    }

    public String getAdoptRate() {
        return adoptRate;
    }

    public void setAdoptRate(String adoptRate) {
        this.adoptRate = adoptRate;
    }

    public String getUnAdoptRate() {
        return UnAdoptRate;
    }

    public void setUnAdoptRate(String unAdoptRate) {
        UnAdoptRate = unAdoptRate;
    }

    public LocalDateTime getMinCreateAt() {
        return minCreateAt;
    }

    public void setMinCreateAt(LocalDateTime minCreateAt) {
        this.minCreateAt = minCreateAt;
    }

    public LocalDateTime getMaxCreateAt() {
        return maxCreateAt;
    }

    public void setMaxCreateAt(LocalDateTime maxCreateAt) {
        this.maxCreateAt = maxCreateAt;
    }

    public String getFsmName() {
        return fsmName;
    }

    public void setFsmName(String fsmName) {
        this.fsmName = fsmName;
    }

    public String getDstState() {
        return dstState;
    }

    public void setDstState(String dstState) {
        this.dstState = dstState;
    }

    public String getOtherState() {
        return otherState;
    }

    public void setOtherState(String otherState) {
        this.otherState = otherState;
    }

    public String getOptUid() {
        return optUid;
    }

    public void setOptUid(String optUid) {
        this.optUid = optUid;
    }

    public Integer getCheckOk() {
        return checkOk;
    }

    public void setCheckOk(Integer checkOk) {
        this.checkOk = checkOk;
    }

    public Integer getCheckWarn() {
        return checkWarn;
    }

    public void setCheckWarn(Integer checkWarn) {
        this.checkWarn = checkWarn;
    }

    public Integer getCheckWarnCheckOk() {
        return checkWarnCheckOk;
    }

    public void setCheckWarnCheckOk(Integer checkWarnCheckOk) {
        this.checkWarnCheckOk = checkWarnCheckOk;
    }

    public Integer getDuplication() {
        return duplication == null ? 0 : duplication;
    }

    public void setDuplication(Integer duplication) {
        this.duplication = duplication;
    }

    public Integer getDuplicationCheckOk() {
        return duplicationCheckOk;
    }

    public void setDuplicationCheckOk(Integer duplicationCheckOk) {
        this.duplicationCheckOk = duplicationCheckOk;
    }

    public Integer getDeptNumber() {
        return deptNumber;
    }

    public void setDeptNumber(Integer deptNumber) {
        this.deptNumber = deptNumber;
    }

    public Integer getCancel() {
        return cancel;
    }

    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }

    public Integer getDraft() {
        return draft;
    }

    public void setDraft(Integer draft) {
        this.draft = draft;
    }

    public Integer getRefuse() {
        return refuse;
    }

    public void setRefuse(Integer refuse) {
        this.refuse = refuse;
    }

    public Integer getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        DepartmentId = departmentId;
    }

    public Integer getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Integer checkerId) {
        this.checkerId = checkerId;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDateTime getStateTime() {
        return stateTime;
    }

    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }
}
