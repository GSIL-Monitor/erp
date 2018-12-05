package com.stosz.store.service;

import com.stosz.store.ext.model.PlanRecord;
import com.stosz.store.mapper.PlanRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:PlanRecordService
 * @Created Time:2017/11/27 14:58
 * @Update Time:2017/11/27 14:58
 */
@Service
public class PlanRecordService {

    @Resource
    private PlanRecordMapper planRecordMapper;

    /**
     * 生成排程表记录
     *
     * @param year month 需要生成的排程表年份 月份
     * @return planId
     */
    public Integer insert(int year, int month) {

        Assert.isTrue(year > 2016, "生成排程表时年份有误");
        Assert.isTrue(month > 0 && month < 13, "生成排程表时月份有误");

        PlanRecord planRecord = findOne(year, month);
        if (planRecord.getId() != null)
            return planRecord.getId();
        else {
            planRecord.setPlanYear(year);
            planRecord.setPlanMonth(month);
            planRecord.setBeginTime(LocalDateTime.of(year, month, 1, 0, 0));
            planRecord.setEndTime(LocalDateTime.of(year, month, 1, 0, 0).minusSeconds(1));
            planRecord.setState("0");
            planRecord.setCreateAt(LocalDateTime.now());
            planRecord.setUpdateAt(LocalDateTime.now());
            planRecordMapper.insert(planRecord);

            return planRecord.getId();
        }
    }

    /**
     * 生成排程表记录
     *
     * @param
     */
    public PlanRecord findOne(Integer year, Integer month) {

        Assert.notNull(year, "年份不能为空");
        Assert.notNull(month, "月份不能为空");
        PlanRecord planRecordQuery = new PlanRecord();
        planRecordQuery.setPlanYear(year);
        planRecordQuery.setPlanMonth(month);
        List<PlanRecord> planRecords = planRecordMapper.query(planRecordQuery);
        PlanRecord planRecord = new PlanRecord();
        if (planRecords.size() > 0)
            planRecord = planRecords.get(0);

        return planRecord;
    }

    /**
     * 获取当前时间排程id
     *
     * @return planId
     */
    public Integer nowPlanId() {

        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonth().getValue();
        PlanRecord planRecord = findOne(year, month);
        Assert.notNull(planRecord, "无当月排程记录");
        return planRecord.getId();
    }

    /**
     * 获取排程
     *
     * @return planId
     */
    public List<PlanRecord> getPlanRecordList(PlanRecord planRecord) {

        return planRecordMapper.query(planRecord);
    }
}
