package com.stosz.plat.enums;

/**
 * yanghuaqing
 * 17/4/25
 */
public enum FuzzyType {
    LEFT(" %s like '%%${%s}}'"), RIGHT(" %s like '${%s}%%'"), INCLUDE(" %s like '%%${%s}%%'");

    private String format;

    FuzzyType(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
