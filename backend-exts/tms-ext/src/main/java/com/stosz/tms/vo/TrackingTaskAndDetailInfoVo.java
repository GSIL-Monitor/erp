package com.stosz.tms.vo;

import com.google.common.collect.Lists;
import com.stosz.tms.model.TrackingTask;
import com.stosz.tms.model.TrackingTaskDetail;

import java.io.Serializable;
import java.util.List;

public class TrackingTaskAndDetailInfoVo implements Serializable {

    private TrackingTask trackingTask = new TrackingTask();

    private List<TrackingTaskDetail> trackingTaskDetails = Lists.newLinkedList() ;


    public TrackingTask getTrackingTask() {
        return trackingTask;
    }

    public void setTrackingTask(TrackingTask trackingTask) {
        this.trackingTask = trackingTask;
    }

    public List<TrackingTaskDetail> getTrackingTaskDetails() {
        return trackingTaskDetails;
    }

    public void setTrackingTaskDetails(List<TrackingTaskDetail> trackingTaskDetails) {
        this.trackingTaskDetails = trackingTaskDetails;
    }
}
