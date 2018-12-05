package com.stosz.admin.ext.model;

import java.util.List;

/**
 * @author liufeng
 * @version [1.0 , 2017/9/13]
 */
public class ElementPermission {
    private Integer jobId;
    private Integer menuId;
    private List<Element> elements;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "ElementPermission{" +
                "jobId=" + jobId +
                ", menuId=" + menuId +
                ", elements=" + elements +
                '}';
    }
}
