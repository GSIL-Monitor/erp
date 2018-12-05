/**
 * Ec_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.qhy;

public class Ec_ServiceLocator extends org.apache.axis.client.Service implements com.stosz.tms.external.qhy.Ec_Service {

    public Ec_ServiceLocator() {
    }


    public Ec_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Ec_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EcSOAP
    private java.lang.String EcSOAP_address = "http://tms.alljoylogistics.com/default/svc/web-service";

    public java.lang.String getEcSOAPAddress() {
        return EcSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EcSOAPWSDDServiceName = "EcSOAP";

    public java.lang.String getEcSOAPWSDDServiceName() {
        return EcSOAPWSDDServiceName;
    }

    public void setEcSOAPWSDDServiceName(java.lang.String name) {
        EcSOAPWSDDServiceName = name;
    }

    public com.stosz.tms.external.qhy.Ec_PortType getEcSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EcSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEcSOAP(endpoint);
    }

    public com.stosz.tms.external.qhy.Ec_PortType getEcSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.stosz.tms.external.qhy.EcSOAPStub _stub = new com.stosz.tms.external.qhy.EcSOAPStub(portAddress, this);
            _stub.setPortName(getEcSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEcSOAPEndpointAddress(java.lang.String address) {
        EcSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.stosz.tms.external.qhy.Ec_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.stosz.tms.external.qhy.EcSOAPStub _stub = new com.stosz.tms.external.qhy.EcSOAPStub(new java.net.URL(EcSOAP_address), this);
                _stub.setPortName(getEcSOAPWSDDServiceName());
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
        if ("EcSOAP".equals(inputPortName)) {
            return getEcSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.example.org/Ec/", "Ec");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.example.org/Ec/", "EcSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EcSOAP".equals(portName)) {
            setEcSOAPEndpointAddress(address);
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
