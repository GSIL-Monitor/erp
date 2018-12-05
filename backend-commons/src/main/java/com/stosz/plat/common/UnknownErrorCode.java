package com.stosz.plat.common;

public class UnknownErrorCode implements CommonErrorCode {

    String desc;
    String name;

    public UnknownErrorCode(Throwable ex) {
        this.name = CommonErrorCode.FAIL.name();
        this.desc = CommonErrorCode.FAIL + ":" + ex.getMessage();
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public String name() {
        return this.name;
    }

}
