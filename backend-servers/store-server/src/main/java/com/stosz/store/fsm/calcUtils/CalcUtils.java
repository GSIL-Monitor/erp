package com.stosz.store.fsm.calcUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalcUtils {

    private static final Logger logger = LoggerFactory.getLogger(CalcUtils.class);

    /**
     * 求平均值
     *
     * @param total
     * @param qty
     * @return
     */
    public static List<BigDecimal> getAvgs(BigDecimal total, Integer qty) {
        Assert.isTrue(total.compareTo(BigDecimal.ZERO) >= 0, "总额必须大于等于0");
        Assert.isTrue(qty >= 0, "数量必须大于0");
        List<BigDecimal> resList = new ArrayList<>();
        if (total.compareTo(BigDecimal.ZERO) == 0 || qty <= 1) {
            resList.add(total);
            return resList;
        }
        BigDecimal avg = total.divide(new BigDecimal(qty), 2, BigDecimal.ROUND_HALF_UP);
        for (int i = 0; i < qty; ++i) {
            if (i == qty - 1) {
                resList.add(total.subtract(avg.multiply(new BigDecimal(i))));
            } else {
                resList.add(avg);
            }
        }
        return resList;
    }


    public static void main(String[] args) {
        BigDecimal total = new BigDecimal(1000);
        Integer qty = 3;
        List<BigDecimal> avgs = getAvgs(total, qty);

    }
}
