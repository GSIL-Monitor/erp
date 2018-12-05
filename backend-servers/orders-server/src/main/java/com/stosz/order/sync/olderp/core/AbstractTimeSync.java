package com.stosz.order.sync.olderp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 按时间段同步
 * @param <T>
 */
public abstract class AbstractTimeSync<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Integer syncSizeEachTime = 200;

    protected LocalDateTime beginTime =  LocalDateTime.of(1970, 1, 1, 1, 1);

    protected LocalDateTime endTime = LocalDateTime.of(2020, 1, 1, 1, 1);


    protected abstract List<T> fetch(Integer offset, Integer limit, LocalDateTime beginTime, LocalDateTime endTime);

    protected abstract Integer count();

    protected abstract Integer insertBatch(List<T> list);


    public void sync(){

        Integer sum = count();
        if (sum == null || sum == 0) {
            logger.info("没有数据，同步结束");
            return;
        }
        long startTime = System.currentTimeMillis();
        int fragment =(int)Math.ceil((double)sum / syncSizeEachTime) ;
        for (int i = 0; i < fragment; i++) {
             List<T> list = fetch(i * syncSizeEachTime, syncSizeEachTime, beginTime, endTime);
             insertBatch(list);
        }
        long minute = (System.currentTimeMillis()- startTime)/(1000 * 1000 * 60);
        logger.info("同步结束，共处理了{}条数据,耗时{}秒",sum, minute);

    }




}
