/**
 * Ec_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.qhy;

public interface Ec_Service extends javax.xml.rpc.Service {
    public java.lang.String getEcSOAPAddress();

    public com.stosz.tms.external.qhy.Ec_PortType getEcSOAP() throws javax.xml.rpc.ServiceException;

    public com.stosz.tms.external.qhy.Ec_PortType getEcSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
