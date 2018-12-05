/**
 * ShipmentTrackingRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex.trace;

public class ShipmentTrackingRequest  implements java.io.Serializable {
    private com.stosz.tms.external.aramex.trace.ClientInfo clientInfo;

    private com.stosz.tms.external.aramex.trace.Transaction transaction;

    private java.lang.String[] shipments;

    private java.lang.Boolean getLastTrackingUpdateOnly;

    public ShipmentTrackingRequest() {
    }

    public ShipmentTrackingRequest(
           com.stosz.tms.external.aramex.trace.ClientInfo clientInfo,
           com.stosz.tms.external.aramex.trace.Transaction transaction,
           java.lang.String[] shipments,
           java.lang.Boolean getLastTrackingUpdateOnly) {
           this.clientInfo = clientInfo;
           this.transaction = transaction;
           this.shipments = shipments;
           this.getLastTrackingUpdateOnly = getLastTrackingUpdateOnly;
    }


    /**
     * Gets the clientInfo value for this ShipmentTrackingRequest.
     * 
     * @return clientInfo
     */
    public com.stosz.tms.external.aramex.trace.ClientInfo getClientInfo() {
        return clientInfo;
    }


    /**
     * Sets the clientInfo value for this ShipmentTrackingRequest.
     * 
     * @param clientInfo
     */
    public void setClientInfo(com.stosz.tms.external.aramex.trace.ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }


    /**
     * Gets the transaction value for this ShipmentTrackingRequest.
     * 
     * @return transaction
     */
    public com.stosz.tms.external.aramex.trace.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this ShipmentTrackingRequest.
     * 
     * @param transaction
     */
    public void setTransaction(com.stosz.tms.external.aramex.trace.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the shipments value for this ShipmentTrackingRequest.
     * 
     * @return shipments
     */
    public java.lang.String[] getShipments() {
        return shipments;
    }


    /**
     * Sets the shipments value for this ShipmentTrackingRequest.
     * 
     * @param shipments
     */
    public void setShipments(java.lang.String[] shipments) {
        this.shipments = shipments;
    }


    /**
     * Gets the getLastTrackingUpdateOnly value for this ShipmentTrackingRequest.
     * 
     * @return getLastTrackingUpdateOnly
     */
    public java.lang.Boolean getGetLastTrackingUpdateOnly() {
        return getLastTrackingUpdateOnly;
    }


    /**
     * Sets the getLastTrackingUpdateOnly value for this ShipmentTrackingRequest.
     * 
     * @param getLastTrackingUpdateOnly
     */
    public void setGetLastTrackingUpdateOnly(java.lang.Boolean getLastTrackingUpdateOnly) {
        this.getLastTrackingUpdateOnly = getLastTrackingUpdateOnly;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentTrackingRequest)) return false;
        ShipmentTrackingRequest other = (ShipmentTrackingRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.clientInfo==null && other.getClientInfo()==null) || 
             (this.clientInfo!=null &&
              this.clientInfo.equals(other.getClientInfo()))) &&
            ((this.transaction==null && other.getTransaction()==null) || 
             (this.transaction!=null &&
              this.transaction.equals(other.getTransaction()))) &&
            ((this.shipments==null && other.getShipments()==null) || 
             (this.shipments!=null &&
              java.util.Arrays.equals(this.shipments, other.getShipments()))) &&
            ((this.getLastTrackingUpdateOnly==null && other.getGetLastTrackingUpdateOnly()==null) || 
             (this.getLastTrackingUpdateOnly!=null &&
              this.getLastTrackingUpdateOnly.equals(other.getGetLastTrackingUpdateOnly())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getClientInfo() != null) {
            _hashCode += getClientInfo().hashCode();
        }
        if (getTransaction() != null) {
            _hashCode += getTransaction().hashCode();
        }
        if (getShipments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getShipments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getShipments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGetLastTrackingUpdateOnly() != null) {
            _hashCode += getGetLastTrackingUpdateOnly().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentTrackingRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentTrackingRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ClientInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ClientInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transaction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Transaction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Transaction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getLastTrackingUpdateOnly");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "GetLastTrackingUpdateOnly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
