/**
 * PickupTrackingResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex.trace;

public class PickupTrackingResponse  implements java.io.Serializable {
    private com.stosz.tms.external.aramex.trace.Transaction transaction;

    private com.stosz.tms.external.aramex.trace.Notification[] notifications;

    private java.lang.Boolean hasErrors;

    private java.lang.String entity;

    private java.lang.String reference;

    private java.util.Calendar collectionDate;

    private java.util.Calendar pickupDate;

    private java.lang.String lastStatus;

    private java.lang.String lastStatusDescription;

    public PickupTrackingResponse() {
    }

    public PickupTrackingResponse(
           com.stosz.tms.external.aramex.trace.Transaction transaction,
           com.stosz.tms.external.aramex.trace.Notification[] notifications,
           java.lang.Boolean hasErrors,
           java.lang.String entity,
           java.lang.String reference,
           java.util.Calendar collectionDate,
           java.util.Calendar pickupDate,
           java.lang.String lastStatus,
           java.lang.String lastStatusDescription) {
           this.transaction = transaction;
           this.notifications = notifications;
           this.hasErrors = hasErrors;
           this.entity = entity;
           this.reference = reference;
           this.collectionDate = collectionDate;
           this.pickupDate = pickupDate;
           this.lastStatus = lastStatus;
           this.lastStatusDescription = lastStatusDescription;
    }


    /**
     * Gets the transaction value for this PickupTrackingResponse.
     * 
     * @return transaction
     */
    public com.stosz.tms.external.aramex.trace.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this PickupTrackingResponse.
     * 
     * @param transaction
     */
    public void setTransaction(com.stosz.tms.external.aramex.trace.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the notifications value for this PickupTrackingResponse.
     * 
     * @return notifications
     */
    public com.stosz.tms.external.aramex.trace.Notification[] getNotifications() {
        return notifications;
    }


    /**
     * Sets the notifications value for this PickupTrackingResponse.
     * 
     * @param notifications
     */
    public void setNotifications(com.stosz.tms.external.aramex.trace.Notification[] notifications) {
        this.notifications = notifications;
    }


    /**
     * Gets the hasErrors value for this PickupTrackingResponse.
     * 
     * @return hasErrors
     */
    public java.lang.Boolean getHasErrors() {
        return hasErrors;
    }


    /**
     * Sets the hasErrors value for this PickupTrackingResponse.
     * 
     * @param hasErrors
     */
    public void setHasErrors(java.lang.Boolean hasErrors) {
        this.hasErrors = hasErrors;
    }


    /**
     * Gets the entity value for this PickupTrackingResponse.
     * 
     * @return entity
     */
    public java.lang.String getEntity() {
        return entity;
    }


    /**
     * Sets the entity value for this PickupTrackingResponse.
     * 
     * @param entity
     */
    public void setEntity(java.lang.String entity) {
        this.entity = entity;
    }


    /**
     * Gets the reference value for this PickupTrackingResponse.
     * 
     * @return reference
     */
    public java.lang.String getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this PickupTrackingResponse.
     * 
     * @param reference
     */
    public void setReference(java.lang.String reference) {
        this.reference = reference;
    }


    /**
     * Gets the collectionDate value for this PickupTrackingResponse.
     * 
     * @return collectionDate
     */
    public java.util.Calendar getCollectionDate() {
        return collectionDate;
    }


    /**
     * Sets the collectionDate value for this PickupTrackingResponse.
     * 
     * @param collectionDate
     */
    public void setCollectionDate(java.util.Calendar collectionDate) {
        this.collectionDate = collectionDate;
    }


    /**
     * Gets the pickupDate value for this PickupTrackingResponse.
     * 
     * @return pickupDate
     */
    public java.util.Calendar getPickupDate() {
        return pickupDate;
    }


    /**
     * Sets the pickupDate value for this PickupTrackingResponse.
     * 
     * @param pickupDate
     */
    public void setPickupDate(java.util.Calendar pickupDate) {
        this.pickupDate = pickupDate;
    }


    /**
     * Gets the lastStatus value for this PickupTrackingResponse.
     * 
     * @return lastStatus
     */
    public java.lang.String getLastStatus() {
        return lastStatus;
    }


    /**
     * Sets the lastStatus value for this PickupTrackingResponse.
     * 
     * @param lastStatus
     */
    public void setLastStatus(java.lang.String lastStatus) {
        this.lastStatus = lastStatus;
    }


    /**
     * Gets the lastStatusDescription value for this PickupTrackingResponse.
     * 
     * @return lastStatusDescription
     */
    public java.lang.String getLastStatusDescription() {
        return lastStatusDescription;
    }


    /**
     * Sets the lastStatusDescription value for this PickupTrackingResponse.
     * 
     * @param lastStatusDescription
     */
    public void setLastStatusDescription(java.lang.String lastStatusDescription) {
        this.lastStatusDescription = lastStatusDescription;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PickupTrackingResponse)) return false;
        PickupTrackingResponse other = (PickupTrackingResponse) obj;
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
            ((this.entity==null && other.getEntity()==null) || 
             (this.entity!=null &&
              this.entity.equals(other.getEntity()))) &&
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              this.reference.equals(other.getReference()))) &&
            ((this.collectionDate==null && other.getCollectionDate()==null) || 
             (this.collectionDate!=null &&
              this.collectionDate.equals(other.getCollectionDate()))) &&
            ((this.pickupDate==null && other.getPickupDate()==null) || 
             (this.pickupDate!=null &&
              this.pickupDate.equals(other.getPickupDate()))) &&
            ((this.lastStatus==null && other.getLastStatus()==null) || 
             (this.lastStatus!=null &&
              this.lastStatus.equals(other.getLastStatus()))) &&
            ((this.lastStatusDescription==null && other.getLastStatusDescription()==null) || 
             (this.lastStatusDescription!=null &&
              this.lastStatusDescription.equals(other.getLastStatusDescription())));
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
        if (getEntity() != null) {
            _hashCode += getEntity().hashCode();
        }
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        if (getCollectionDate() != null) {
            _hashCode += getCollectionDate().hashCode();
        }
        if (getPickupDate() != null) {
            _hashCode += getPickupDate().hashCode();
        }
        if (getLastStatus() != null) {
            _hashCode += getLastStatus().hashCode();
        }
        if (getLastStatusDescription() != null) {
            _hashCode += getLastStatusDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PickupTrackingResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">PickupTrackingResponse"));
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
        elemField.setFieldName("entity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Entity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Reference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CollectionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pickupDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LastStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastStatusDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LastStatusDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
