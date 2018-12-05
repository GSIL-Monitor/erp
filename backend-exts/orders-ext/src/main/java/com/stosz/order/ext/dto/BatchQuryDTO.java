package com.stosz.order.ext.dto;

import java.util.List;

public class BatchQuryDTO {

    private List<String> succ;

    private List<String> fail;

    public List<String> getSucc() {
        return succ;
    }

    public void setSucc(List<String> succ) {
        this.succ = succ;
    }

    public List<String> getFail() {
        return fail;
    }

    public void setFail(List<String> fail) {
        this.fail = fail;
    }
}
