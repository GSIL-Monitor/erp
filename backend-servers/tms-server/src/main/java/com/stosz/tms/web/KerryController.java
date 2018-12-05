package com.stosz.tms.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.model.TrackingTaskShippingDetail;
import com.stosz.tms.model.api.KerryReqBody;
import com.stosz.tms.model.api.KerryStatus;
import com.stosz.tms.service.TrackingTaskShippingDetailService;
import com.stosz.tms.task.LogisticsTrackTaskImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tms/kerry")
public class KerryController extends AbstractController {
    private static Logger logger= LoggerFactory.getLogger(KerryController.class);
    private static Integer batchCount=1000;
//    private static org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(KerryPostController.class);

    @Autowired
    private TrackingTaskShippingDetailService taskShippingDetailService;

    @RequestMapping(value = "shipment_status",method = RequestMethod.POST)
    @ResponseBody
    public String shipment_status(@RequestBody KerryReqBody kerryReqBody){
        String res=null;
        KerryStatus status=kerryReqBody.getReq().getStatus();
        TrackingTaskDetail trackingTaskDetail=new TrackingTaskDetail();
        trackingTaskDetail.setTrackNo(status.getCon_no());

        Assert.notNull(status.getCon_no(),"null trackNo");
        Assert.notNull(status.getLocation(),"null location");
        Assert.notNull(status.getStatus_desc(),"null statusDesc");
        Assert.notNull(status.getUpdate_date(),"null date");

        List<TrackingTaskDetail> batchTaskDetails = new ArrayList<>();
        trackingTaskDetail.setTrackNo(status.getCon_no());
        trackingTaskDetail.setCityName(status.getLocation());
        trackingTaskDetail.setApiCode(status.getStatus_code());
        trackingTaskDetail.setTrackStatus(status.getStatus_desc());
        Date date=null;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             date=simpleDateFormat.parse(status.getUpdate_date());
        } catch (ParseException e) {
            logger.info("时间转换失败{}",e.getMessage());
           res= "{\"res\": {\"status\": {\"status_code\": \"999\", \"status_desc\": \"failed\"}}}";
            return res;
        }
        trackingTaskDetail.setCreateAt(date);
        batchTaskDetails.add(trackingTaskDetail);
        int countTask=0;

//        if ((batchTaskDetails.size() >= batchCount || countTask == trackingTasks.size()) && ArrayUtils.isNotEmpty(batchTaskDetails)) {
            LogisticsTrackTaskImpl logisticsTrackTask=new LogisticsTrackTaskImpl();

            List<TrackingTaskShippingDetail> taskShippingDetails = logisticsTrackTask.transferShippingDetails(batchTaskDetails);
            List<String> hashValues = taskShippingDetails.stream().map(TrackingTaskShippingDetail::getHashValue).collect(Collectors.toList());
            HashSet<String> containsSet = taskShippingDetailService.queryDetailHashExists(hashValues);
            List<TrackingTaskShippingDetail> addShippingDetails = taskShippingDetails.stream()
                    .filter(item -> !containsSet.contains(item.getTrackNo())).collect(Collectors.toList());
            if (ArrayUtils.isNotEmpty(addShippingDetails)) {
                taskShippingDetailService.addTrackDeatils(addShippingDetails);
            }
            batchTaskDetails.clear();
//        }

//        taskShippingDetailService.insertKerryTrack(status);
//              taskShippingDetailService.addTrackDeatils(addShippingDetails);
        res="{\"res\": {\"status\": {\"status_code\": \"000\", \"status_desc\": \"Successful\"}}}";
        return res;
    }
    @RequestMapping(value = "shipment",method = RequestMethod.POST)
    @ResponseBody
    public String shipment(@RequestBody String kerryReqBody){
        String a=kerryReqBody;
        return  a;
    }
}
