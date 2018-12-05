package com.stosz.order.ext.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 退货凭证图片
 */
public class PhotosAlbumDTO {
    //相册标题
    private String title ="退货/拒收相册";
    //相册id
    private String id ="001";
    //初始显示的图片序号，默认0
    private Integer start = 0;
    //相册包含的图片，数组格式
    private List<Photo> data = new ArrayList<Photo>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = "退货/拒收相册";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = "001";
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public List<Photo> getData() {
        return data;
    }

    public void setData(List<Photo> data) {
        this.data = data;
    }
}
