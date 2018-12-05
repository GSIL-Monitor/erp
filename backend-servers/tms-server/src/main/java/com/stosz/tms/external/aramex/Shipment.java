/**
 * Shipment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class Shipment  implements java.io.Serializable {
    private java.lang.String reference1;

    private java.lang.String reference2;

    private java.lang.String reference3;

    private com.stosz.tms.external.aramex.Party shipper;

    private com.stosz.tms.external.aramex.Party consignee;

    private com.stosz.tms.external.aramex.Party thirdParty;

    private java.util.Calendar shippingDateTime;

    private java.util.Calendar dueDate;

    private java.lang.String comments;

    private java.lang.String pickupLocation;

    private java.lang.String operationsInstructions;

    private java.lang.String accountingInstrcutions;

    private com.stosz.tms.external.aramex.ShipmentDetails details;

    private com.stosz.tms.external.aramex.Attachment[] attachments;

    private java.lang.String foreignHAWB;

    private java.lang.Integer transportType_x0020_;

    private java.lang.String pickupGUID;

    private java.lang.String number;

    private com.stosz.tms.external.aramex.ScheduledDelivery scheduledDelivery;

    public Shipment() {
    }

    public Shipment(
           java.lang.String reference1,
           java.lang.String reference2,
           java.lang.String reference3,
           com.stosz.tms.external.aramex.Party shipper,
           com.stosz.tms.external.aramex.Party consignee,
           com.stosz.tms.external.aramex.Party thirdParty,
           java.util.Calendar shippingDateTime,
           java.util.Calendar dueDate,
           java.lang.String comments,
           java.lang.String pickupLocation,
           java.lang.String operationsInstructions,
           java.lang.String accountingInstrcutions,
           com.stosz.tms.external.aramex.ShipmentDetails details,
           com.stosz.tms.external.aramex.Attachment[] attachments,
           java.lang.String foreignHAWB,
           java.lang.Integer transportType_x0020_,
           java.lang.String pickupGUID,
           java.lang.String number,
           com.stosz.tms.external.aramex.ScheduledDelivery scheduledDelivery) {
           this.reference1 = reference1;
           this.reference2 = reference2;
           this.reference3 = reference3;
           this.shipper = shipper;
           this.consignee = consignee;
           this.thirdParty = thirdParty;
           this.shippingDateTime = shippingDateTime;
           this.dueDate = dueDate;
           this.comments = comments;
           this.pickupLocation = pickupLocation;
           this.operationsInstructions = operationsInstructions;
           this.accountingInstrcutions = accountingInstrcutions;
           this.details = details;
           this.attachments = attachments;
           this.foreignHAWB = foreignHAWB;
           this.transportType_x0020_ = transportType_x0020_;
           this.pickupGUID = pickupGUID;
           this.number = number;
           this.scheduledDelivery = scheduledDelivery;
    }


    /**
     * Gets the reference1 value for this Shipment.
     *
     * @return reference1
     */
    public java.lang.String getReference1() {
        return reference1;
    }


    /**
     * Sets the reference1 value for this Shipment.
     *
     * @param reference1
     */
    public void setReference1(java.lang.String reference1) {
        this.reference1 = reference1;
    }


    /**
     * Gets the reference2 value for this Shipment.
     *
     * @return reference2
     */
    public java.lang.String getReference2() {
        return reference2;
    }


    /**
     * Sets the reference2 value for this Shipment.
     *
     * @param reference2
     */
    public void setReference2(java.lang.String reference2) {
        this.reference2 = reference2;
    }


    /**
     * Gets the reference3 value for this Shipment.
     *
     * @return reference3
     */
    public java.lang.String getReference3() {
        return reference3;
    }


    /**
     * Sets the reference3 value for this Shipment.
     *
     * @param reference3
     */
    public void setReference3(java.lang.String reference3) {
        this.reference3 = reference3;
    }


    /**
     * Gets the shipper value for this Shipment.
     *
     * @return shipper
     */
    public com.stosz.tms.external.aramex.Party getShipper() {
        return shipper;
    }


    /**
     * Sets the shipper value for this Shipment.
     *
     * @param shipper
     */
    public void setShipper(com.stosz.tms.external.aramex.Party shipper) {
        this.shipper = shipper;
    }


    /**
     * Gets the consignee value for this Shipment.
     *
     * @return consignee
     */
    public com.stosz.tms.external.aramex.Party getConsignee() {
        return consignee;
    }


    /**
     * Sets the consignee value for this Shipment.
     *
     * @param consignee
     */
    public void setConsignee(com.stosz.tms.external.aramex.Party consignee) {
        this.consignee = consignee;
    }


    /**
     * Gets the thirdParty value for this Shipment.
     *
     * @return thirdParty
     */
    public com.stosz.tms.external.aramex.Party getThirdParty() {
        return thirdParty;
    }


    /**
     * Sets the thirdParty value for this Shipment.
     *
     * @param thirdParty
     */
    public void setThirdParty(com.stosz.tms.external.aramex.Party thirdParty) {
        this.thirdParty = thirdParty;
    }


    /**
     * Gets the shippingDateTime value for this Shipment.
     *
     * @return shippingDateTime
     */
    public java.util.Calendar getShippingDateTime() {
        return shippingDateTime;
    }


    /**
     * Sets the shippingDateTime value for this Shipment.
     *
     * @param shippingDateTime
     */
    public void setShippingDateTime(java.util.Calendar shippingDateTime) {
        this.shippingDateTime = shippingDateTime;
    }


    /**
     * Gets the dueDate value for this Shipment.
     *
     * @return dueDate
     */
    public java.util.Calendar getDueDate() {
        return dueDate;
    }


    /**
     * Sets the dueDate value for this Shipment.
     *
     * @param dueDate
     */
    public void setDueDate(java.util.Calendar dueDate) {
        this.dueDate = dueDate;
    }


    /**
     * Gets the comments value for this Shipment.
     *
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this Shipment.
     *
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the pickupLocation value for this Shipment.
     *
     * @return pickupLocation
     */
    public java.lang.String getPickupLocation() {
        return pickupLocation;
    }


    /**
     * Sets the pickupLocation value for this Shipment.
     *
     * @param pickupLocation
     */
    public void setPickupLocation(java.lang.String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }


    /**
     * Gets the operationsInstructions value for this Shipment.
     *
     * @return operationsInstructions
     */
    public java.lang.String getOperationsInstructions() {
        return operationsInstructions;
    }


    /**
     * Sets the operationsInstructions value for this Shipment.
     *
     * @param operationsInstructions
     */
    public void setOperationsInstructions(java.lang.String operationsInstructions) {
        this.operationsInstructions = operationsInstructions;
    }


    /**
     * Gets the accountingInstrcutions value for this Shipment.
     *
     * @return accountingInstrcutions
     */
    public java.lang.String getAccountingInstrcutions() {
        return accountingInstrcutions;
    }


    /**
     * Sets the accountingInstrcutions value for this Shipment.
     *
     * @param accountingInstrcutions
     */
    public void setAccountingInstrcutions(java.lang.String accountingInstrcutions) {
        this.accountingInstrcutions = accountingInstrcutions;
    }


    /**
     * Gets the details value for this Shipment.
     *
     * @return details
     */
    public com.stosz.tms.external.aramex.ShipmentDetails getDetails() {
        return details;
    }


    /**
     * Sets the details value for this Shipment.
     *
     * @param details
     */
    public void setDetails(com.stosz.tms.external.aramex.ShipmentDetails details) {
        this.details = details;
    }


    /**
     * Gets the attachments value for this Shipment.
     *
     * @return attachments
     */
    public com.stosz.tms.external.aramex.Attachment[] getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this Shipment.
     *
     * @param attachments
     */
    public void setAttachments(com.stosz.tms.external.aramex.Attachment[] attachments) {
        this.attachments = attachments;
    }


    /**
     * Gets the foreignHAWB value for this Shipment.
     *
     * @return foreignHAWB
     */
    public java.lang.String getForeignHAWB() {
        return foreignHAWB;
    }


    /**
     * Sets the foreignHAWB value for this Shipment.
     *
     * @param foreignHAWB
     */
    public void setForeignHAWB(java.lang.String foreignHAWB) {
        this.foreignHAWB = foreignHAWB;
    }


    /**
     * Gets the transportType_x0020_ value for this Shipment.
     *
     * @return transportType_x0020_
     */
    public java.lang.Integer getTransportType_x0020_() {
        return transportType_x0020_;
    }


    /**
     * Sets the transportType_x0020_ value for this Shipment.
     *
     * @param transportType_x0020_
     */
    public void setTransportType_x0020_(java.lang.Integer transportType_x0020_) {
        this.transportType_x0020_ = transportType_x0020_;
    }


    /**
     * Gets the pickupGUID value for this Shipment.
     *
     * @return pickupGUID
     */
    public java.lang.String getPickupGUID() {
        return pickupGUID;
    }


    /**
     * Sets the pickupGUID value for this Shipment.
     *
     * @param pickupGUID
     */
    public void setPickupGUID(java.lang.String pickupGUID) {
        this.pickupGUID = pickupGUID;
    }


    /**
     * Gets the number value for this Shipment.
     *
     * @return number
     */
    public java.lang.String getNumber() {
        return number;
    }


    /**
     * Sets the number value for this Shipment.
     *
     * @param number
     */
    public void setNumber(java.lang.String number) {
        this.number = number;
    }


    /**
     * Gets the scheduledDelivery value for this Shipment.
     *
     * @return scheduledDelivery
     */
    public com.stosz.tms.external.aramex.ScheduledDelivery getScheduledDelivery() {
        return scheduledDelivery;
    }


    /**
     * Sets the scheduledDelivery value for this Shipment.
     *
     * @param scheduledDelivery
     */
    public void setScheduledDelivery(com.stosz.tms.external.aramex.ScheduledDelivery scheduledDelivery) {
        this.scheduledDelivery = scheduledDelivery;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Shipment)) return false;
        Shipment other = (Shipment) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.reference1==null && other.getReference1()==null) ||
             (this.reference1!=null &&
              this.reference1.equals(other.getReference1()))) &&
            ((this.reference2==null && other.getReference2()==null) ||
             (this.reference2!=null &&
              this.reference2.equals(other.getReference2()))) &&
            ((this.reference3==null && other.getReference3()==null) ||
             (this.reference3!=null &&
              this.reference3.equals(other.getReference3()))) &&
            ((this.shipper==null && other.getShipper()==null) ||
             (this.shipper!=null &&
              this.shipper.equals(other.getShipper()))) &&
            ((this.consignee==null && other.getConsignee()==null) ||
             (this.consignee!=null &&
              this.consignee.equals(other.getConsignee()))) &&
            ((this.thirdParty==null && other.getThirdParty()==null) ||
             (this.thirdParty!=null &&
              this.thirdParty.equals(other.getThirdParty()))) &&
            ((this.shippingDateTime==null && other.getShippingDateTime()==null) ||
             (this.shippingDateTime!=null &&
              this.shippingDateTime.equals(other.getShippingDateTime()))) &&
            ((this.dueDate==null && other.getDueDate()==null) ||
             (this.dueDate!=null &&
              this.dueDate.equals(other.getDueDate()))) &&
            ((this.comments==null && other.getComments()==null) ||
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.pickupLocation==null && other.getPickupLocation()==null) ||
             (this.pickupLocation!=null &&
              this.pickupLocation.equals(other.getPickupLocation()))) &&
            ((this.operationsInstructions==null && other.getOperationsInstructions()==null) ||
             (this.operationsInstructions!=null &&
              this.operationsInstructions.equals(other.getOperationsInstructions()))) &&
            ((this.accountingInstrcutions==null && other.getAccountingInstrcutions()==null) ||
             (this.accountingInstrcutions!=null &&
              this.accountingInstrcutions.equals(other.getAccountingInstrcutions()))) &&
            ((this.details==null && other.getDetails()==null) ||
             (this.details!=null &&
              this.details.equals(other.getDetails()))) &&
            ((this.attachments==null && other.getAttachments()==null) ||
             (this.attachments!=null &&
              java.util.Arrays.equals(this.attachments, other.getAttachments()))) &&
            ((this.foreignHAWB==null && other.getForeignHAWB()==null) ||
             (this.foreignHAWB!=null &&
              this.foreignHAWB.equals(other.getForeignHAWB()))) &&
            ((this.transportType_x0020_==null && other.getTransportType_x0020_()==null) ||
             (this.transportType_x0020_!=null &&
              this.transportType_x0020_.equals(other.getTransportType_x0020_()))) &&
            ((this.pickupGUID==null && other.getPickupGUID()==null) ||
             (this.pickupGUID!=null &&
              this.pickupGUID.equals(other.getPickupGUID()))) &&
            ((this.number==null && other.getNumber()==null) ||
             (this.number!=null &&
              this.number.equals(other.getNumber()))) &&
            ((this.scheduledDelivery==null && other.getScheduledDelivery()==null) ||
             (this.scheduledDelivery!=null &&
              this.scheduledDelivery.equals(other.getScheduledDelivery())));
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
        if (getReference1() != null) {
            _hashCode += getReference1().hashCode();
        }
        if (getReference2() != null) {
            _hashCode += getReference2().hashCode();
        }
        if (getReference3() != null) {
            _hashCode += getReference3().hashCode();
        }
        if (getShipper() != null) {
            _hashCode += getShipper().hashCode();
        }
        if (getConsignee() != null) {
            _hashCode += getConsignee().hashCode();
        }
        if (getThirdParty() != null) {
            _hashCode += getThirdParty().hashCode();
        }
        if (getShippingDateTime() != null) {
            _hashCode += getShippingDateTime().hashCode();
        }
        if (getDueDate() != null) {
            _hashCode += getDueDate().hashCode();
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getPickupLocation() != null) {
            _hashCode += getPickupLocation().hashCode();
        }
        if (getOperationsInstructions() != null) {
            _hashCode += getOperationsInstructions().hashCode();
        }
        if (getAccountingInstrcutions() != null) {
            _hashCode += getAccountingInstrcutions().hashCode();
        }
        if (getDetails() != null) {
            _hashCode += getDetails().hashCode();
        }
        if (getAttachments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttachments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttachments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getForeignHAWB() != null) {
            _hashCode += getForeignHAWB().hashCode();
        }
        if (getTransportType_x0020_() != null) {
            _hashCode += getTransportType_x0020_().hashCode();
        }
        if (getPickupGUID() != null) {
            _hashCode += getPickupGUID().hashCode();
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        if (getScheduledDelivery() != null) {
            _hashCode += getScheduledDelivery().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Shipment.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipment"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipper");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipper"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Party"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consignee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Consignee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Party"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thirdParty");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ThirdParty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Party"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shippingDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShippingDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "DueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pickupLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationsInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "OperationsInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountingInstrcutions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "AccountingInstrcutions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("details");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Details"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentDetails"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Attachments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Attachment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Attachment"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foreignHAWB");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ForeignHAWB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transportType_x0020_");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "TransportType_x0020_"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pickupGUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupGUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
