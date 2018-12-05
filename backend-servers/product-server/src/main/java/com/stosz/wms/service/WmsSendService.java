package com.stosz.wms.service;

import com.stosz.plat.utils.HttpClient;
import com.stosz.plat.wms.model.WmsConfig;
import com.stosz.plat.wms.model.WmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/**
 * Created by carter on 2017/7/19. Copyright © 2016 －2017
 * 异步记录日志服务类
 */
public class WmsSendService {


    private volatile  static WmsSendService instance;

    public static final int logWorkThreadNum = 2;

    private static ExecutorService  executorService= Executors.newFixedThreadPool(logWorkThreadNum, r -> {
        final Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setDaemon(true);
        return thread;
    });

    private  static LinkedBlockingQueue<WmsSender> logQueue =new LinkedBlockingQueue<>(50000);


    public static final Logger logger = LoggerFactory.getLogger(WmsSendService.class);


    public static void saveWmsSender(WmsSender logModel)
    {
        if(null != logModel)  logQueue.offer(logModel);
    }



    /**
     * 获取日志服务的实例；
     * @return
     */
    public static WmsSendService getInstance()
    {
        if(null == instance)
        {
            synchronized (WmsSendService.class){

                if(null == instance) instance= new WmsSendService();

            }
        }
        return instance;
    }


    private WmsSendService()
    {

        IntStream.range(0,logWorkThreadNum).forEach(value -> executorService.execute(()->{
            while (true){
                try {
                    //会阻塞至有消息
                     WmsSender logModel = logQueue.take();
                     if(null == logModel) continue;

                    String baseContent = logModel.getJsonContent();
                    WmsConfig wmsConfig = logModel.getWmsConfig();
                    String param = "appkey="+wmsConfig.getAPPKEY()+"&service="+logModel.getUrl()+"&format="+wmsConfig.getFORMAT()+"&encrypt="+wmsConfig.getENCRYPT()+"&content="+baseContent+"&secret="+null;
                    logger.info("发送出去的参数："+param);
                    new HttpClient().pub(wmsConfig.getSERVICE_URL(), param);

                } catch (InterruptedException e) {
                    logger.error("推送wms接口信息异常",e);
                    logger.error(e.getMessage(), e);
                }
            }
        }));

    }





}
