package com.stosz.olderp.ext.model;

import java.util.List;
import java.util.Map;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/5]
 */
public class ImageDomain {

    private List<Map<String, String>> photo;

    private String thumb;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<Map<String, String>> getPhoto() {

        return photo;
    }

    public void setPhoto(List<Map<String, String>> photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "imageDomain{" +
                "photo=" + photo +
                ", thumb='" + thumb + '\'' +
                '}';
    }
}
