package com.stosz.plat.service.deamon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by shiqiangguo on 17/5/12.
 */
//@Component
public class FlowChargeThread implements InitializingBean, Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private boolean running = true;

    @Autowired
    private FlowChargeQueue flowChargeQueue;


    public void stop() {
        while (!flowChargeQueue.isEmpty()) {
            sleep(1000);
        }
        this.running = false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(this).start();
    }

    private void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void run() {
        logger.info("启动充值处理线程，5秒后开始处理队列中的待充值订单");
        sleep(5000);
        while (running) {
            Integer chargeId = null;
            try {
                chargeId = flowChargeQueue.takeFirst();
                logger.info("从队列中获取到一个充流量任务，id:{} ", chargeId);
//                goodsOrderChargeService.doCharge(chargeId);
            } catch (Exception e) {
                logger.error("充值流量异常！，稍后5秒再试！", e);
                sleep(5000);
                flowChargeQueue.putLast(chargeId);
            }
        }
    }

}
