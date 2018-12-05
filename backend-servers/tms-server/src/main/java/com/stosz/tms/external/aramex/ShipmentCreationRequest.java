/**
 * ShipmentCreationRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class ShipmentCreationRequest  implements java.io.Serializable {
    private com.stosz.tms.external.aramex.ClientInfo clientInfo;

    private com.stosz.tms.external.aramex.Transaction transaction;

    private com.stosz.tms.external.aramex.Shipment[] shipments;

    private com.stosz.tms.external.aramex.LabelInfo labelInfo;

    public ShipmentCreationRequest() {
    }

    public ShipmentCreationRequest(
           com.stosz.tms.external.aramex.ClientInfo clientInfo,
           com.stosz.tms.external.aramex.Transaction transaction,
           com.stosz.tms.external.aramex.Shipment[] shipments,
           com.stosz.tms.external.aramex.LabelInfo labelInfo) {
           this.clientInfo = clientInfo;
           this.transaction = transaction;
           this.shipments = shipments;
           this.labelInfo = labelInfo;
    }


    /**
     * Gets the clientInfo value for this ShipmentCreationRequest.
     *
     * @return clientInfo
     */
    public com.stosz.tms.external.aramex.ClientInfo getClientInfo() {
        return clientInfo;
    }


    /**
     * Sets the clientInfo value for this ShipmentCreationRequest.
     *
     * @param clientInfo
     */
    public void setClientInfo(com.stosz.tms.external.aramex.ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }


    /**
     * Gets the transaction value for this ShipmentCreationRequest.
     *
     * @return transaction
     */
    public com.stosz.tms.external.aramex.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this ShipmentCreationRequest.
     *
     * @param transaction
     */
    public void setTransaction(com.stosz.tms.external.aramex.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the shipments value for this ShipmentCreationRequest.
     *
     * @return shipments
     */
    public com.stosz.tms.external.aramex.Shipment[] getShipments() {
        return shipments;
    }


    /**
     * Sets the shipments value for this ShipmentCreationRequest.
     *
     * @param shipments
     */
    public void setShipments(com.stosz.tms.external.aramex.Shipment[] shipments) {
        this.shipments = shipments;
    }


    /**
     * Gets the labelInfo value for this ShipmentCreationRequest.
     *
     * @return labelInfo
     */
    public com.stosz.tms.external.aramex.LabelInfo getLabelInfo() {
        return labelInfo;
    }


    /**
     * Sets the labelInfo value for this ShipmentCreationRequest.
     *
     * @param labelInfo
     */
    public void setLabelInfo(com.stosz.tms.external.aramex.LabelInfo labelInfo) {
        this.labelInfo = labelInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentCreationRequest)) return false;
        ShipmentCreationRequest other = (ShipmentCreationRequest) obj;
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
            ((this.labelInfo==null && other.getLabelInfo()==null) ||
             (this.labelInfo!=null &&
              this.labelInfo.equals(other.getLabelInfo())));
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
        if (getLabelInfo() != null) {
            _hashCode += getLabelInfo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentCreationRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentCreationRequest"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipment"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labelInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LabelInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LabelInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
