/**
 * HoldCreationResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class HoldCreationResponse  implements java.io.Serializable {
    private com.stosz.tms.external.aramex.Transaction transaction;

    private com.stosz.tms.external.aramex.Notification[] notifications;

    private java.lang.Boolean hasErrors;

    private com.stosz.tms.external.aramex.ProcessedShipmentHold[] processedShipmentHolds;

    public HoldCreationResponse() {
    }

    public HoldCreationResponse(
           com.stosz.tms.external.aramex.Transaction transaction,
           com.stosz.tms.external.aramex.Notification[] notifications,
           java.lang.Boolean hasErrors,
           com.stosz.tms.external.aramex.ProcessedShipmentHold[] processedShipmentHolds) {
           this.transaction = transaction;
           this.notifications = notifications;
           this.hasErrors = hasErrors;
           this.processedShipmentHolds = processedShipmentHolds;
    }


    /**
     * Gets the transaction value for this HoldCreationResponse.
     *
     * @return transaction
     */
    public com.stosz.tms.external.aramex.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this HoldCreationResponse.
     *
     * @param transaction
     */
    public void setTransaction(com.stosz.tms.external.aramex.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the notifications value for this HoldCreationResponse.
     *
     * @return notifications
     */
    public com.stosz.tms.external.aramex.Notification[] getNotifications() {
        return notifications;
    }


    /**
     * Sets the notifications value for this HoldCreationResponse.
     *
     * @param notifications
     */
    public void setNotifications(com.stosz.tms.external.aramex.Notification[] notifications) {
        this.notifications = notifications;
    }


    /**
     * Gets the hasErrors value for this HoldCreationResponse.
     *
     * @return hasErrors
     */
    public java.lang.Boolean getHasErrors() {
        return hasErrors;
    }


    /**
     * Sets the hasErrors value for this HoldCreationResponse.
     *
     * @param hasErrors
     */
    public void setHasErrors(java.lang.Boolean hasErrors) {
        this.hasErrors = hasErrors;
    }


    /**
     * Gets the processedShipmentHolds value for this HoldCreationResponse.
     *
     * @return processedShipmentHolds
     */
    public com.stosz.tms.external.aramex.ProcessedShipmentHold[] getProcessedShipmentHolds() {
        return processedShipmentHolds;
    }


    /**
     * Sets the processedShipmentHolds value for this HoldCreationResponse.
     *
     * @param processedShipmentHolds
     */
    public void setProcessedShipmentHolds(com.stosz.tms.external.aramex.ProcessedShipmentHold[] processedShipmentHolds) {
        this.processedShipmentHolds = processedShipmentHolds;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HoldCreationResponse)) return false;
        HoldCreationResponse other = (HoldCreationResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.transaction==null && other.getTransaction()==null) ||
             (this.transaction!=null &&
              this.transaction.equals(other.getTransaction()))) &&
            ((this.notifications==null && other.getNotifications()==null) ||
             (this.notifications!=null &&
              java.util.Arrays.equals(this.notifications, other.getNotifications()))) &&
            ((this.hasErrors==null && other.getHasErrors()==null) ||
             (this.hasErrors!=null &&
              this.hasErrors.equals(other.getHasErrors()))) &&
            ((this.processedShipmentHolds==null && other.getProcessedShipmentHolds()==null) ||
             (this.processedShipmentHolds!=null &&
              java.util.Arrays.equals(this.processedShipmentHolds, other.getProcessedShipmentHolds())));
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
        if (getTransaction() != null) {
            _hashCode += getTransaction().hashCode();
        }
        if (getNotifications() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotifications());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotifications(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getHasErrors() != null) {
            _hashCode += getHasErrors().hashCode();
        }
        if (getProcessedShipmentHolds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProcessedShipmentHolds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProcessedShipmentHolds(), i);
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
        new org.apache.axis.description.TypeDesc(HoldCreationResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">HoldCreationResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transaction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Transaction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Transaction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notifications");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Notifications"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Notification"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Notification"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasErrors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "HasErrors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processedShipmentHolds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentHolds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentHold"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentHold"));
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
