/**
 * ShipmentTrackingResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex.trace;

public class ShipmentTrackingResponse  implements java.io.Serializable {
    private com.stosz.tms.external.aramex.trace.Transaction transaction;

    private com.stosz.tms.external.aramex.trace.Notification[] notifications;

    private java.lang.Boolean hasErrors;

    private com.stosz.tms.external.aramex.trace.ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY[] trackingResults;

    private java.lang.String[] nonExistingWaybills;

    public ShipmentTrackingResponse() {
    }

    public ShipmentTrackingResponse(
           com.stosz.tms.external.aramex.trace.Transaction transaction,
           com.stosz.tms.external.aramex.trace.Notification[] notifications,
           java.lang.Boolean hasErrors,
           com.stosz.tms.external.aramex.trace.ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY[] trackingResults,
           java.lang.String[] nonExistingWaybills) {
           this.transaction = transaction;
           this.notifications = notifications;
           this.hasErrors = hasErrors;
           this.trackingResults = trackingResults;
           this.nonExistingWaybills = nonExistingWaybills;
    }


    /**
     * Gets the transaction value for this ShipmentTrackingResponse.
     * 
     * @return transaction
     */
    public com.stosz.tms.external.aramex.trace.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this ShipmentTrackingResponse.
     * 
     * @param transaction
     */
    public void setTransaction(com.stosz.tms.external.aramex.trace.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the notifications value for this ShipmentTrackingResponse.
     * 
     * @return notifications
     */
    public com.stosz.tms.external.aramex.trace.Notification[] getNotifications() {
        return notifications;
    }


    /**
     * Sets the notifications value for this ShipmentTrackingResponse.
     * 
     * @param notifications
     */
    public void setNotifications(com.stosz.tms.external.aramex.trace.Notification[] notifications) {
        this.notifications = notifications;
    }


    /**
     * Gets the hasErrors value for this ShipmentTrackingResponse.
     * 
     * @return hasErrors
     */
    public java.lang.Boolean getHasErrors() {
        return hasErrors;
    }


    /**
     * Sets the hasErrors value for this ShipmentTrackingResponse.
     * 
     * @param hasErrors
     */
    public void setHasErrors(java.lang.Boolean hasErrors) {
        this.hasErrors = hasErrors;
    }


    /**
     * Gets the trackingResults value for this ShipmentTrackingResponse.
     * 
     * @return trackingResults
     */
    public com.stosz.tms.external.aramex.trace.ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY[] getTrackingResults() {
        return trackingResults;
    }


    /**
     * Sets the trackingResults value for this ShipmentTrackingResponse.
     * 
     * @param trackingResults
     */
    public void setTrackingResults(com.stosz.tms.external.aramex.trace.ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpYKeyValueOfstringArrayOfTrackingResultmFAkxlpY[] trackingResults) {
        this.trackingResults = trackingResults;
    }


    /**
     * Gets the nonExistingWaybills value for this ShipmentTrackingResponse.
     * 
     * @return nonExistingWaybills
     */
    public java.lang.String[] getNonExistingWaybills() {
        return nonExistingWaybills;
    }


    /**
     * Sets the nonExistingWaybills value for this ShipmentTrackingResponse.
     * 
     * @param nonExistingWaybills
     */
    public void setNonExistingWaybills(java.lang.String[] nonExistingWaybills) {
        this.nonExistingWaybills = nonExistingWaybills;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentTrackingResponse)) return false;
        ShipmentTrackingResponse other = (ShipmentTrackingResponse) obj;
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
            ((this.trackingResults==null && other.getTrackingResults()==null) || 
             (this.trackingResults!=null &&
              java.util.Arrays.equals(this.trackingResults, other.getTrackingResults()))) &&
            ((this.nonExistingWaybills==null && other.getNonExistingWaybills()==null) || 
             (this.nonExistingWaybills!=null &&
              java.util.Arrays.equals(this.nonExistingWaybills, other.getNonExistingWaybills())));
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
        if (getTrackingResults() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTrackingResults());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTrackingResults(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNonExistingWaybills() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNonExistingWaybills());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNonExistingWaybills(), i);
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
        new org.apache.axis.description.TypeDesc(ShipmentTrackingResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ShipmentTrackingResponse"));
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
        elemField.setFieldName("trackingResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "TrackingResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", ">ArrayOfKeyValueOfstringArrayOfTrackingResultmFAkxlpY>KeyValueOfstringArrayOfTrackingResultmFAkxlpY"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "KeyValueOfstringArrayOfTrackingResultmFAkxlpY"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nonExistingWaybills");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "NonExistingWaybills"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
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
