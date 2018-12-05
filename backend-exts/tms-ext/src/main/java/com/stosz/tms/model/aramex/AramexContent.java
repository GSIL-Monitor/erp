package com.stosz.tms.model.aramex;

import java.io.Serializable;
import java.util.List;

/**
 * Aramex content 实体
 * @author xiepengcheng
 * @version [1.0,2017年12月15日]
 */
public class AramexContent implements Serializable{

	private List<ClientInfo> clientInfo;

	private List<Transaction> transaction;

	private List<Shipment> shipments;

	private List<LabelInfo> labelInfo;

	public List<ClientInfo> getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(List<ClientInfo> clientInfo) {
		this.clientInfo = clientInfo;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	public List<Shipment> getShipments() {
		return shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public List<LabelInfo> getLabelInfo() {
		return labelInfo;
	}

	public void setLabelInfo(List<LabelInfo> labelInfo) {
		this.labelInfo = labelInfo;
	}
}
