/**
 * ScheduledDeliveryRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class ScheduledDeliveryRequest  implements java.io.Serializable {
    private com.stosz.tms.external.aramex.ClientInfo clientInfo;

    private com.stosz.tms.external.aramex.Transaction transaction;

    private com.stosz.tms.external.aramex.Address address;

    private com.stosz.tms.external.aramex.ScheduledDelivery scheduledDelivery;

    private java.lang.String shipmentNumber;

    private java.lang.String productGroup;

    private java.lang.String entity;

    private java.lang.String consigneePhone;

    private java.lang.String shipperNumber;

    private java.lang.String shipperReference;

    private java.lang.String reference1;

    private java.lang.String reference2;

    private java.lang.String reference3;

    public ScheduledDeliveryRequest() {
    }

    public ScheduledDeliveryRequest(
           com.stosz.tms.external.aramex.ClientInfo clientInfo,
           com.stosz.tms.external.aramex.Transaction transaction,
           com.stosz.tms.external.aramex.Address address,
           com.stosz.tms.external.aramex.ScheduledDelivery scheduledDelivery,
           java.lang.String shipmentNumber,
           java.lang.String productGroup,
           java.lang.String entity,
           java.lang.String consigneePhone,
           java.lang.String shipperNumber,
           java.lang.String shipperReference,
           java.lang.String reference1,
           java.lang.String reference2,
           java.lang.String reference3) {
           this.clientInfo = clientInfo;
           this.transaction = transaction;
           this.address = address;
           this.scheduledDelivery = scheduledDelivery;
           this.shipmentNumber = shipmentNumber;
           this.productGroup = productGroup;
           this.entity = entity;
           this.consigneePhone = consigneePhone;
           this.shipperNumber = shipperNumber;
           this.shipperReference = shipperReference;
           this.reference1 = reference1;
           this.reference2 = reference2;
           this.reference3 = reference3;
    }


    /**
     * Gets the clientInfo value for this ScheduledDeliveryRequest.
     *
     * @return clientInfo
     */
    public com.stosz.tms.external.aramex.ClientInfo getClientInfo() {
        return clientInfo;
    }


    /**
     * Sets the clientInfo value for this ScheduledDeliveryRequest.
     *
     * @param clientInfo
     */
    public void setClientInfo(com.stosz.tms.external.aramex.ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }


    /**
     * Gets the transaction value for this ScheduledDeliveryRequest.
     *
     * @return transaction
     */
    public com.stosz.tms.external.aramex.Transaction getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this ScheduledDeliveryRequest.
     *
     * @param transaction
     */
    public void setTransaction(com.stosz.tms.external.aramex.Transaction transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the address value for this ScheduledDeliveryRequest.
     *
     * @return address
     */
    public com.stosz.tms.external.aramex.Address getAddress() {
        return address;
    }


    /**
     * Sets the address value for this ScheduledDeliveryRequest.
     *
     * @param address
     */
    public void setAddress(com.stosz.tms.external.aramex.Address address) {
        this.address = address;
    }


    /**
     * Gets the scheduledDelivery value for this ScheduledDeliveryRequest.
     *
     * @return scheduledDelivery
     */
    public com.stosz.tms.external.aramex.ScheduledDelivery getScheduledDelivery() {
        return scheduledDelivery;
    }


    /**
     * Sets the scheduledDelivery value for this ScheduledDeliveryRequest.
     *
     * @param scheduledDelivery
     */
    public void setScheduledDelivery(com.stosz.tms.external.aramex.ScheduledDelivery scheduledDelivery) {
        this.scheduledDelivery = scheduledDelivery;
    }


    /**
     * Gets the shipmentNumber value for this ScheduledDeliveryRequest.
     *
     * @return shipmentNumber
     */
    public java.lang.String getShipmentNumber() {
        return shipmentNumber;
    }


    /**
     * Sets the shipmentNumber value for this ScheduledDeliveryRequest.
     *
     * @param shipmentNumber
     */
    public void setShipmentNumber(java.lang.String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }


    /**
     * Gets the productGroup value for this ScheduledDeliveryRequest.
     *
     * @return productGroup
     */
    public java.lang.String getProductGroup() {
        return productGroup;
    }


    /**
     * Sets the productGroup value for this ScheduledDeliveryRequest.
     *
     * @param productGroup
     */
    public void setProductGroup(java.lang.String productGroup) {
        this.productGroup = productGroup;
    }


    /**
     * Gets the entity value for this ScheduledDeliveryRequest.
     *
     * @return entity
     */
    public java.lang.String getEntity() {
        return entity;
    }


    /**
     * Sets the entity value for this ScheduledDeliveryRequest.
     *
     * @param entity
     */
    public void setEntity(java.lang.String entity) {
        this.entity = entity;
    }


    /**
     * Gets the consigneePhone value for this ScheduledDeliveryRequest.
     *
     * @return consigneePhone
     */
    public java.lang.String getConsigneePhone() {
        return consigneePhone;
    }


    /**
     * Sets the consigneePhone value for this ScheduledDeliveryRequest.
     *
     * @param consigneePhone
     */
    public void setConsigneePhone(java.lang.String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }


    /**
     * Gets the shipperNumber value for this ScheduledDeliveryRequest.
     *
     * @return shipperNumber
     */
    public java.lang.String getShipperNumber() {
        return shipperNumber;
    }


    /**
     * Sets the shipperNumber value for this ScheduledDeliveryRequest.
     *
     * @param shipperNumber
     */
    public void setShipperNumber(java.lang.String shipperNumber) {
        this.shipperNumber = shipperNumber;
    }


    /**
     * Gets the shipperReference value for this ScheduledDeliveryRequest.
     *
     * @return shipperReference
     */
    public java.lang.String getShipperReference() {
        return shipperReference;
    }


    /**
     * Sets the shipperReference value for this ScheduledDeliveryRequest.
     *
     * @param shipperReference
     */
    public void setShipperReference(java.lang.String shipperReference) {
        this.shipperReference = shipperReference;
    }


    /**
     * Gets the reference1 value for this ScheduledDeliveryRequest.
     *
     * @return reference1
     */
    public java.lang.String getReference1() {
        return reference1;
    }


    /**
     * Sets the reference1 value for this ScheduledDeliveryRequest.
     *
     * @param reference1
     */
    public void setReference1(java.lang.String reference1) {
        this.reference1 = reference1;
    }


    /**
     * Gets the reference2 value for this ScheduledDeliveryRequest.
     *
     * @return reference2
     */
    public java.lang.String getReference2() {
        return reference2;
    }


    /**
     * Sets the reference2 value for this ScheduledDeliveryRequest.
     *
     * @param reference2
     */
    public void setReference2(java.lang.String reference2) {
        this.reference2 = reference2;
    }


    /**
     * Gets the reference3 value for this ScheduledDeliveryRequest.
     *
     * @return reference3
     */
    public java.lang.String getReference3() {
        return reference3;
    }


    /**
     * Sets the reference3 value for this ScheduledDeliveryRequest.
     *
     * @param reference3
     */
    public void setReference3(java.lang.String reference3) {
        this.reference3 = reference3;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ScheduledDeliveryRequest)) return false;
        ScheduledDeliveryRequest other = (ScheduledDeliveryRequest) obj;
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
            ((this.address==null && other.getAddress()==null) ||
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.scheduledDelivery==null && other.getScheduledDelivery()==null) ||
             (this.scheduledDelivery!=null &&
              this.scheduledDelivery.equals(other.getScheduledDelivery()))) &&
            ((this.shipmentNumber==null && other.getShipmentNumber()==null) ||
             (this.shipmentNumber!=null &&
              this.shipmentNumber.equals(other.getShipmentNumber()))) &&
            ((this.productGroup==null && other.getProductGroup()==null) ||
             (this.productGroup!=null &&
              this.productGroup.equals(other.getProductGroup()))) &&
            ((this.entity==null && other.getEntity()==null) ||
             (this.entity!=null &&
              this.entity.equals(other.getEntity()))) &&
            ((this.consigneePhone==null && other.getConsigneePhone()==null) ||
             (this.consigneePhone!=null &&
              this.consigneePhone.equals(other.getConsigneePhone()))) &&
            ((this.shipperNumber==null && other.getShipperNumber()==null) ||
             (this.shipperNumber!=null &&
              this.shipperNumber.equals(other.getShipperNumber()))) &&
            ((this.shipperReference==null && other.getShipperReference()==null) ||
             (this.shipperReference!=null &&
              this.shipperReference.equals(other.getShipperReference()))) &&
            ((this.reference1==null && other.getReference1()==null) ||
             (this.reference1!=null &&
              this.reference1.equals(other.getReference1()))) &&
            ((this.reference2==null && other.getReference2()==null) ||
             (this.reference2!=null &&
              this.reference2.equals(other.getReference2()))) &&
            ((this.reference3==null && other.getReference3()==null) ||
             (this.reference3!=null &&
              this.reference3.equals(other.getReference3())));
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
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getScheduledDelivery() != null) {
            _hashCode += getScheduledDelivery().hashCode();
        }
        if (getShipmentNumber() != null) {
            _hashCode += getShipmentNumber().hashCode();
        }
        if (getProductGroup() != null) {
            _hashCode += getProductGroup().hashCode();
        }
        if (getEntity() != null) {
            _hashCode += getEntity().hashCode();
        }
        if (getConsigneePhone() != null) {
            _hashCode += getConsigneePhone().hashCode();
        }
        if (getShipperNumber() != null) {
            _hashCode += getShipperNumber().hashCode();
        }
        if (getShipperReference() != null) {
            _hashCode += getShipperReference().hashCode();
        }
        if (getReference1() != null) {
            _hashCode += getReference1().hashCode();
        }
        if (getReference2() != null) {
            _hashCode += getReference2().hashCode();
        }
        if (getReference3() != null) {
            _hashCode += getReference3().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ScheduledDeliveryRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", ">ScheduledDeliveryRequest"));
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
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scheduledDelivery");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ScheduledDelivery"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ScheduledDelivery"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProductGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Entity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consigneePhone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ConsigneePhone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipperNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipperNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipperReference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipperReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Reference1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Reference2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Reference3"));
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
