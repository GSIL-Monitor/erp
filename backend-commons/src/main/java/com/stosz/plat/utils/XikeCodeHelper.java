package com.stosz.plat.utils;

import org.springframework.util.Assert;

import java.text.DecimalFormat;

public class XikeCodeHelper {

    /**
     * 格式化送货单号，GF - 供应商发货的意思
     */
    private static final DecimalFormat deliveryOrderFormat = new DecimalFormat("GF-000000000");

    public static String deliveryOrderIdFormat(Integer deliveryOrderId) {
        return codeFormat(deliveryOrderId, deliveryOrderFormat);
    }

    private static final String goodsIdPrefix = "WP-";
    private static final DecimalFormat goodsFormat = new DecimalFormat(goodsIdPrefix + "000000000");

    public static String goodsIdFormat(Integer goodsId) {
        return codeFormat(goodsId, goodsFormat);
    }

    private static String codeFormat(Integer id, DecimalFormat format) {
        return id == null ? null : format.format(id);
    }

    public static Integer goodsIdTrim(String goodsIdStr) {
        return idTrim(goodsIdStr, goodsIdPrefix);
    }

    private static Integer idTrim(String idStr, String prefix) {
        Assert.notNull(idStr, "输入的编码是空");
        Assert.isTrue(!idStr.isEmpty(), "输入的编码是空串");
        Integer goodsId = null;
        try {
            goodsId = Integer.parseInt(idStr);
        } catch (Exception e) {
            //不是纯数字,时否是WP开头
            if (idStr.startsWith("WP-") || idStr.startsWith("wp-")) {
                try {
                    goodsId = Integer.parseInt(idStr.substring(3));
                } catch (Exception e1) {

                }
            }
        }
        return goodsId;

        //		if (idStr.startsWith(prefix)) {
        //			idStr = idStr.replaceFirst(prefix, "");
        //		}
        //		if (!RegxUtil.checkDigit(idStr)) {
        //			return null;
        //		}
        //		return new Integer(idStr);
    }

}
