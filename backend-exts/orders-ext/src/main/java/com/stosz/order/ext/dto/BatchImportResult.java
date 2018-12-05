package com.stosz.order.ext.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther tangtao
 * create time  2018/1/8
 */
public class BatchImportResult {
    Integer totalCount = 0;
    Integer successCount = 0;
    Integer failCount = 0;
    Boolean hasWrong = false;
    String failListKey = "";//导入失败列表json数据 redis key
    List<Object> failList = new ArrayList<>();
//    List<String> failStrList = new ArrayList<>();

//    public List<String> getFailStrList() {
//        return failStrList;
//    }
//
//    public void setFailStrList(List<String> failStrList) {
//        this.failStrList = failStrList;
//    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }


    public Boolean getHasWrong() {
        return hasWrong;
    }

    public void setHasWrong(Boolean hasWrong) {
        this.hasWrong = hasWrong;
    }

    public List<Object> getFailList() {
        return failList;
    }

    public void setFailList(List<Object> failList) {
        this.failList = failList;
    }


    public String getFailListKey() {
        return failListKey;
    }

    public void setFailListKey(String failListKey) {
        this.failListKey = failListKey;
    }

    public static class FailedImport {
        String transNo;
        String failReason;

        public FailedImport(String transNo, String failReason) {
            this.transNo = transNo;
            this.failReason = failReason;
        }

        public String getTransNo() {
            return transNo;
        }

        public void setTransNo(String transNo) {
            this.transNo = transNo;
        }

        public String getFailReason() {
            return failReason;
        }

        public void setFailReason(String failReason) {
            this.failReason = failReason;
        }
    }
}



