/**
 * HoldCreationRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class HoldCreationRequest  implements java.io.Serializable {
    private com.stosz.tms.external.aramex.ClientInfo clientInfo;

    private com.stosz.tms.external.aramex.Transaction transaction;

    private com.stosz.tms.external.aramex.ShipmentHoldDetails[] shipmentHolds;

    public HoldCreationRequest() {
    }

    public HoldCreationRequest(
           com.stosz.tms.external.aramex.ClientInfo clientInfo,
           com.stosz.tms.external.aramex.Transaction transaction,
           com.stosz.tms.external.aramex.ShipmentHoldDetails[] shipmentHolds) {
           this.clientInfo = clientInfo;
           this.transaction = transaction;
           this.shipmentHolds = shipmentHolds;
    }


    /**
     * Gets the clientInfo value for this HoldCreationRequest.
     *
     * @return clientInfo
     */
    public com.stosz.tms.external.aramex.ClientInfo getClientInfo() {
        return clientInfo;
    }


    /**
     * Sets the clientInfo value for this HoldCreationRequest.
     *
     * @param clientInfo
     */
    public void setClientInfo(com.stosz.tms.external.aramex.ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }


    /**
     * Gets the transaction value for this HoldCreationRequest.
     *
     * @return transaction
     */
    public com.stosz.tms.external.aramex.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this HoldCreationRequest.
     *
     * @param transaction
     */
    public void setTransaction(com.stosz.tms.external.aramex.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the shipmentHolds value for this HoldCreationRequest.
     *
     * @return shipmentHolds
     */
    public com.stosz.tms.external.aramex.ShipmentHoldDetails[] getShipmentHolds() {
        return shipmentHolds;
    }


    /**
     * Sets the shipmentHolds value for this HoldCreationRequest.
     *
     * @param shipmentHolds
     */
    public void setShipmentHolds(com.stosz.tms.external.aramex.ShipmentHoldDetails[] shipmentHolds) {
        this.shipmentHolds = shipmentHolds;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HoldCreationRequest)) return false;
        HoldCreationRequest other = (HoldCreationRequest) obj;
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
            ((this.shipmentHolds==null && other.getShipmentHolds()==null) ||
             (this.shipmentHolds!=null &&
              java.util.Arrays.equals(this.shipmentHolds, other.getShipmentHolds())));
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
        if (getShipmentHolds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getShipmentHolds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getShipmentHolds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HoldCreationRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">HoldCreationRequest"));
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
        elemField.setFieldName("shipmentHolds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentHolds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentHoldDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentHoldDetails"));
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
