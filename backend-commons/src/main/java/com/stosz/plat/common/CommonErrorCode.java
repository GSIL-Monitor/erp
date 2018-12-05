package com.stosz.plat.common;

public interface CommonErrorCode {

    public static final CommonErrorCode FAIL = new CommonErrorCode() {

        public String getDesc() {
            return "未知错误";
        }

        public String name() {
            return "ERROR";
        }
    };

    public static final CommonErrorCode OK = new CommonErrorCode() {

        public String getDesc() {
            return "成功";
        }

        public String name() {
            return "OK";
        }
    };

    String getDesc();

    String name();

}
