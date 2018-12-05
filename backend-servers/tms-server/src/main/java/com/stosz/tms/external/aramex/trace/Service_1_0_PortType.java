/**
 * Service_1_0_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex.trace;

public interface Service_1_0_PortType extends java.rmi.Remote {
    public com.stosz.tms.external.aramex.trace.ShipmentTrackingResponse trackShipments(com.stosz.tms.external.aramex.trace.ShipmentTrackingRequest parameters) throws java.rmi.RemoteException;
    public com.stosz.tms.external.aramex.trace.PickupTrackingResponse trackPickup(com.stosz.tms.external.aramex.trace.PickupTrackingRequest parameters) throws java.rmi.RemoteException;
}
