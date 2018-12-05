package com.stosz.tms.dto;

import java.util.List;

public class TrackingStatusClassifyAddDto {

    private Integer trackApiId;

    private String statusClassifys;

    private List<StatusClassify> statusClassifyList;

    private String creator;

    private Integer creatorId;
    

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getTrackApiId() {
        return trackApiId;
    }

    public void setTrackApiId(Integer trackApiId) {
        this.trackApiId = trackApiId;
    }

    public String getStatusClassifys() {
        return statusClassifys;
    }

    public void setStatusClassifys(String statusClassifys) {
        this.statusClassifys = statusClassifys;
    }

    public List<StatusClassify> getStatusClassifyList() {
        return statusClassifyList;
    }

    public void setStatusClassifyList(List<StatusClassify> statusClassifyList) {
        this.statusClassifyList = statusClassifyList;
    }

    public static class StatusClassify{

        private String  shippingStatus;

        private String classifyStatus;

        private String classifyCode;
        
        private String shippingStatusCode;

        public String getShippingStatusCode() {
			return shippingStatusCode;
		}

		public void setShippingStatusCode(String shippingStatusCode) {
			this.shippingStatusCode = shippingStatusCode;
		}

		public String getClassifyCode() {
            return classifyCode;
        }

        public void setClassifyCode(String classifyCode) {
            this.classifyCode = classifyCode;
        }

        public String getShippingStatus() {
            return shippingStatus;
        }

        public void setShippingStatus(String shippingStatus) {
            this.shippingStatus = shippingStatus;
        }

        public String getClassifyStatus() {
            return classifyStatus;
        }

        public void setClassifyStatus(String classifyStatus) {
            this.classifyStatus = classifyStatus;
        }
    }



}
