package com.stosz.plat.service.deamon;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by shiqiangguo on 17/5/13.
 */
@Component
public class FlowChargeQueue {
    private LinkedBlockingDeque<Integer> flowChargeQueue = new LinkedBlockingDeque<Integer>();

    boolean running = true;

    @PreDestroy
    public void stop() {
        running = false;
    }

    public Integer takeFirst() {
        try {
            return flowChargeQueue.takeFirst();
        } catch (InterruptedException e) {
            return null;
        }
    }

    public void putLast(Integer id) {
        if (id == null) {
            return;
        }

        Assert.isTrue(running, "队列已经停止服务，无法插入！");
        try {
            flowChargeQueue.putLast(id);
        } catch (InterruptedException e) {
            throw new RuntimeException("未能插入到充值队列！");
        }
    }

    public boolean isEmpty() {
        return flowChargeQueue.isEmpty();
    }
}
