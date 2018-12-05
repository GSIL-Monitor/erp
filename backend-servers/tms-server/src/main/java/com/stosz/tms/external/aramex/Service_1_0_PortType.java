/**
 * Service_1_0_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public interface Service_1_0_PortType extends java.rmi.Remote {
    public com.stosz.tms.external.aramex.ShipmentCreationResponse createShipments(com.stosz.tms.external.aramex.ShipmentCreationRequest parameters) throws java.rmi.RemoteException;
    public com.stosz.tms.external.aramex.LabelPrintingResponse printLabel(com.stosz.tms.external.aramex.LabelPrintingRequest parameters) throws java.rmi.RemoteException;
    public com.stosz.tms.external.aramex.PickupCreationResponse createPickup(com.stosz.tms.external.aramex.PickupCreationRequest parameters) throws java.rmi.RemoteException;
    public com.stosz.tms.external.aramex.PickupCancelationResponse cancelPickup(com.stosz.tms.external.aramex.PickupCancelationRequest parameters) throws java.rmi.RemoteException;
    public com.stosz.tms.external.aramex.ReserveRangeResponse reserveShipmentNumberRange(com.stosz.tms.external.aramex.ReserveRangeRequest parameters) throws java.rmi.RemoteException;
    public com.stosz.tms.external.aramex.LastReservedShipmentNumberRangeResponse getLastShipmentsNumbersRange(com.stosz.tms.external.aramex.LastReservedShipmentNumberRangeRequest parameters) throws java.rmi.RemoteException;
    public com.stosz.tms.external.aramex.ScheduledDeliveryResponse scheduleDelivery(com.stosz.tms.external.aramex.ScheduledDeliveryRequest parameters) throws java.rmi.RemoteException;
    public com.stosz.tms.external.aramex.HoldCreationResponse holdShipments(com.stosz.tms.external.aramex.HoldCreationRequest parameters) throws java.rmi.RemoteException;
}
