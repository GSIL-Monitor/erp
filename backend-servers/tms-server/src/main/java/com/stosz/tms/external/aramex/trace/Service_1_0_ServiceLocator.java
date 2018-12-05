/**
 * Service_1_0_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex.trace;

public class Service_1_0_ServiceLocator extends org.apache.axis.client.Service implements com.stosz.tms.external.aramex.trace.Service_1_0_Service {

    public Service_1_0_ServiceLocator() {
    }


    public Service_1_0_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Service_1_0_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_Service_1_01
    private java.lang.String BasicHttpBinding_Service_1_01_address = "https://ws.aramex.net/ShippingAPI.V2/Tracking/Service_1_0.svc";

    public java.lang.String getBasicHttpBinding_Service_1_01Address() {
        return BasicHttpBinding_Service_1_01_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_Service_1_01WSDDServiceName = "BasicHttpBinding_Service_1_01";

    public java.lang.String getBasicHttpBinding_Service_1_01WSDDServiceName() {
        return BasicHttpBinding_Service_1_01WSDDServiceName;
    }

    public void setBasicHttpBinding_Service_1_01WSDDServiceName(java.lang.String name) {
        BasicHttpBinding_Service_1_01WSDDServiceName = name;
    }

    public com.stosz.tms.external.aramex.trace.Service_1_0_PortType getBasicHttpBinding_Service_1_01() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_Service_1_01_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_Service_1_01(endpoint);
    }

    public com.stosz.tms.external.aramex.trace.Service_1_0_PortType getBasicHttpBinding_Service_1_01(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.stosz.tms.external.aramex.trace.BasicHttpBinding_Service_1_01Stub _stub = new com.stosz.tms.external.aramex.trace.BasicHttpBinding_Service_1_01Stub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_Service_1_01WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_Service_1_01EndpointAddress(java.lang.String address) {
        BasicHttpBinding_Service_1_01_address = address;
    }


    // Use to get a proxy class for BasicHttpBinding_Service_1_0
    private java.lang.String BasicHttpBinding_Service_1_0_address = "http://ws.aramex.net/ShippingAPI.V2/Tracking/Service_1_0.svc";

    public java.lang.String getBasicHttpBinding_Service_1_0Address() {
        return BasicHttpBinding_Service_1_0_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_Service_1_0WSDDServiceName = "BasicHttpBinding_Service_1_0";

    public java.lang.String getBasicHttpBinding_Service_1_0WSDDServiceName() {
        return BasicHttpBinding_Service_1_0WSDDServiceName;
    }

    public void setBasicHttpBinding_Service_1_0WSDDServiceName(java.lang.String name) {
        BasicHttpBinding_Service_1_0WSDDServiceName = name;
    }

    public com.stosz.tms.external.aramex.trace.Service_1_0_PortType getBasicHttpBinding_Service_1_0() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_Service_1_0_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_Service_1_0(endpoint);
    }

    public com.stosz.tms.external.aramex.trace.Service_1_0_PortType getBasicHttpBinding_Service_1_0(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.stosz.tms.external.aramex.trace.BasicHttpBinding_Service_1_0Stub _stub = new com.stosz.tms.external.aramex.trace.BasicHttpBinding_Service_1_0Stub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_Service_1_0WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_Service_1_0EndpointAddress(java.lang.String address) {
        BasicHttpBinding_Service_1_0_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.stosz.tms.external.aramex.trace.Service_1_0_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.stosz.tms.external.aramex.trace.BasicHttpBinding_Service_1_01Stub _stub = new com.stosz.tms.external.aramex.trace.BasicHttpBinding_Service_1_01Stub(new java.net.URL(BasicHttpBinding_Service_1_01_address), this);
                _stub.setPortName(getBasicHttpBinding_Service_1_01WSDDServiceName());
                return _stub;
            }
            if (com.stosz.tms.external.aramex.trace.Service_1_0_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.stosz.tms.external.aramex.trace.BasicHttpBinding_Service_1_0Stub _stub = new com.stosz.tms.external.aramex.trace.BasicHttpBinding_Service_1_0Stub(new java.net.URL(BasicHttpBinding_Service_1_0_address), this);
                _stub.setPortName(getBasicHttpBinding_Service_1_0WSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_Service_1_01".equals(inputPortName)) {
            return getBasicHttpBinding_Service_1_01();
        }
        else if ("BasicHttpBinding_Service_1_0".equals(inputPortName)) {
            return getBasicHttpBinding_Service_1_0();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Service_1_0");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "BasicHttpBinding_Service_1_01"));
            ports.add(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "BasicHttpBinding_Service_1_0"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_Service_1_01".equals(portName)) {
            setBasicHttpBinding_Service_1_01EndpointAddress(address);
        }
        else 
if ("BasicHttpBinding_Service_1_0".equals(portName)) {
            setBasicHttpBinding_Service_1_0EndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
