package com.stosz.plat.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonException extends RuntimeException {

    private static final long serialVersionUID = -238091758285157331L;

    private CommonErrorCode xikeErrorCode;

    private List<String> errParam = new ArrayList<>();

    public CommonException(Throwable cause) {
        this(cause, new UnknownErrorCode(cause), cause.getMessage());

    }

    public CommonException(Throwable cause, CommonErrorCode xikeErrorCode) {
        this(cause, xikeErrorCode, new Object[] {});
    }

    public CommonException(Throwable cause, CommonErrorCode xikeErrorCode, Object... errParam) {
        super(xikeErrorCode.getDesc(), cause);
        this.xikeErrorCode = xikeErrorCode;
        this.setErrParam(errParam);
    }

    public CommonException(CommonErrorCode xikeErrorCode) {
        super(xikeErrorCode.getDesc());
        this.xikeErrorCode = xikeErrorCode;
    }

    public CommonException(CommonErrorCode xikeErrorCode, Object... errParam) {
        super(xikeErrorCode.getDesc());
        this.xikeErrorCode = xikeErrorCode;
        this.setErrParam(errParam);
    }

    public CommonException(String desc) {
        super(desc);
        this.xikeErrorCode = CommonErrorCode.FAIL;
    }

    public CommonException(String desc, Throwable cause) {
        super(desc, cause);
        if (cause instanceof CommonException) {
            CommonException e = (CommonException) cause;
            this.xikeErrorCode = e.getXikeErrorCode();
            this.errParam = e.getErrParam();
        } else {
            this.xikeErrorCode = CommonErrorCode.FAIL;
        }
    }

    public CommonException setXikeErrorCode(CommonErrorCode xikeErrorCode) {
        this.xikeErrorCode = xikeErrorCode;
        return this;
    }

    public CommonErrorCode getXikeErrorCode() {
        return xikeErrorCode;
    }

    public CommonException setErrParam(Object... param) {
        this.errParam = Arrays.asList(param).stream().map(String::valueOf)
                .collect(Collectors.toList());
        return this;
    }

    public CommonException setErrParam(String... param) {
        this.errParam = Arrays.asList(param);
        return this;
    }

    public CommonException setErrParam(List<Object> ps) {
        if (ps != null) {
            this.errParam = ps.stream().map(e -> e.toString()).collect(Collectors.toList());
        }
        return this;
    }

    public List<String> getErrParam() {
        return errParam;
    }

    public String getDescribe() {
        if (this.xikeErrorCode == null) {
            return super.getMessage();
        }
        // FAIL("处理异常")
        if (this.xikeErrorCode == CommonErrorCode.FAIL) {
            return super.getMessage();
        }

        if (this.getErrParam() == null || this.getErrParam().isEmpty()) {
            return xikeErrorCode.getDesc();
        }
        return MessageFormat.format(xikeErrorCode.getDesc(), this.getErrParam().toArray());
    }
}
