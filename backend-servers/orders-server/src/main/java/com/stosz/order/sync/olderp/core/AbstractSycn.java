package com.stosz.order.sync.olderp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther carter
 * create time    2017-11-07
 *
 * 初始化数据的拉取数据，后面数据变化的数据推送；
 */
@Component
public abstract class AbstractSycn<T> {

    private  int fetch_count = 2000;
    private static final Logger logger  = LoggerFactory.getLogger(AbstractSycn.class);

    public void setFetch_count(int fetch_count) {
        this.fetch_count = fetch_count;
    }

    /**
     * 按照id范围抓取
     * @param idMin  id最小值
     * @param idMax  id最大值
     * @return
     */
    protected abstract List<T> fetchByIdRegion(int idMin, int idMax);
    protected abstract String dataDesc();
    protected abstract int getOldMaxId();
    protected abstract int getNewMaxId();
    protected abstract Integer batchInsert(List<T> item);
    /**
     * 对于状态不会改变的数据，按照id增量拉取
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean pullById()
    {
        //1,得到总条数
        int oldMaxId =  getOldMaxId();
        int newMaxId =  getNewMaxId();
        final AtomicInteger[] atomicInteger = {new AtomicInteger(0)};

        if(oldMaxId>newMaxId)
        {
            //2,批量插入
            long startTime = System.currentTimeMillis();
            List<T>  fetchTList = fetchByIdRegion(newMaxId,newMaxId+fetch_count);
            Optional.ofNullable(fetchTList).ifPresent((List<T> item) -> atomicInteger[0] = new AtomicInteger(batchInsert(item)));
            //3,汇总结果
            long endTime = System.currentTimeMillis();
            logger.info("id增量"+dataDesc()+"数据,本批次处理" + fetchTList.size() + "条,成功处理"+ atomicInteger[0].get()+"条，耗时(秒)：{}", (endTime - startTime)/1000);
            return atomicInteger[0].get() > 0;
        }
       return false;

    }

}
