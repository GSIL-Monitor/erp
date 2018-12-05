package com.stosz.tms.model.philippine;

import java.io.Serializable;
import java.util.List;

public class PhilippineTrackDetails implements Serializable{

    private Boolean result;

    List<PhilippineTrackResponseData> data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<PhilippineTrackResponseData> getData() {
        return data;
    }

    public void setData(List<PhilippineTrackResponseData> data) {
        this.data = data;
    }
}
