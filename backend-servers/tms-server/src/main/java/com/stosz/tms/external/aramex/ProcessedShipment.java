/**
 * ProcessedShipment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class ProcessedShipment  implements java.io.Serializable {
    private java.lang.String ID;

    private java.lang.String reference1;

    private java.lang.String reference2;

    private java.lang.String reference3;

    private java.lang.String foreignHAWB;

    private boolean hasErrors;

    private com.stosz.tms.external.aramex.Notification[] notifications;

    private com.stosz.tms.external.aramex.ShipmentLabel shipmentLabel;

    private com.stosz.tms.external.aramex.ProcessedShipmentDetails shipmentDetails;

    private com.stosz.tms.external.aramex.ProcessedShipmentAttachment[] shipmentAttachments;

    public ProcessedShipment() {
    }

    public ProcessedShipment(
           java.lang.String ID,
           java.lang.String reference1,
           java.lang.String reference2,
           java.lang.String reference3,
           java.lang.String foreignHAWB,
           boolean hasErrors,
           com.stosz.tms.external.aramex.Notification[] notifications,
           com.stosz.tms.external.aramex.ShipmentLabel shipmentLabel,
           com.stosz.tms.external.aramex.ProcessedShipmentDetails shipmentDetails,
           com.stosz.tms.external.aramex.ProcessedShipmentAttachment[] shipmentAttachments) {
           this.ID = ID;
           this.reference1 = reference1;
           this.reference2 = reference2;
           this.reference3 = reference3;
           this.foreignHAWB = foreignHAWB;
           this.hasErrors = hasErrors;
           this.notifications = notifications;
           this.shipmentLabel = shipmentLabel;
           this.shipmentDetails = shipmentDetails;
           this.shipmentAttachments = shipmentAttachments;
    }


    /**
     * Gets the ID value for this ProcessedShipment.
     *
     * @return ID
     */
    public java.lang.String getID() {
        return ID;
    }


    /**
     * Sets the ID value for this ProcessedShipment.
     *
     * @param ID
     */
    public void setID(java.lang.String ID) {
        this.ID = ID;
    }


    /**
     * Gets the reference1 value for this ProcessedShipment.
     *
     * @return reference1
     */
    public java.lang.String getReference1() {
        return reference1;
    }


    /**
     * Sets the reference1 value for this ProcessedShipment.
     *
     * @param reference1
     */
    public void setReference1(java.lang.String reference1) {
        this.reference1 = reference1;
    }


    /**
     * Gets the reference2 value for this ProcessedShipment.
     *
     * @return reference2
     */
    public java.lang.String getReference2() {
        return reference2;
    }


    /**
     * Sets the reference2 value for this ProcessedShipment.
     *
     * @param reference2
     */
    public void setReference2(java.lang.String reference2) {
        this.reference2 = reference2;
    }


    /**
     * Gets the reference3 value for this ProcessedShipment.
     *
     * @return reference3
     */
    public java.lang.String getReference3() {
        return reference3;
    }


    /**
     * Sets the reference3 value for this ProcessedShipment.
     *
     * @param reference3
     */
    public void setReference3(java.lang.String reference3) {
        this.reference3 = reference3;
    }


    /**
     * Gets the foreignHAWB value for this ProcessedShipment.
     *
     * @return foreignHAWB
     */
    public java.lang.String getForeignHAWB() {
        return foreignHAWB;
    }


    /**
     * Sets the foreignHAWB value for this ProcessedShipment.
     *
     * @param foreignHAWB
     */
    public void setForeignHAWB(java.lang.String foreignHAWB) {
        this.foreignHAWB = foreignHAWB;
    }


    /**
     * Gets the hasErrors value for this ProcessedShipment.
     *
     * @return hasErrors
     */
    public boolean isHasErrors() {
        return hasErrors;
    }


    /**
     * Sets the hasErrors value for this ProcessedShipment.
     *
     * @param hasErrors
     */
    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }


    /**
     * Gets the notifications value for this ProcessedShipment.
     *
     * @return notifications
     */
    public com.stosz.tms.external.aramex.Notification[] getNotifications() {
        return notifications;
    }


    /**
     * Sets the notifications value for this ProcessedShipment.
     *
     * @param notifications
     */
    public void setNotifications(com.stosz.tms.external.aramex.Notification[] notifications) {
        this.notifications = notifications;
    }


    /**
     * Gets the shipmentLabel value for this ProcessedShipment.
     *
     * @return shipmentLabel
     */
    public com.stosz.tms.external.aramex.ShipmentLabel getShipmentLabel() {
        return shipmentLabel;
    }


    /**
     * Sets the shipmentLabel value for this ProcessedShipment.
     *
     * @param shipmentLabel
     */
    public void setShipmentLabel(com.stosz.tms.external.aramex.ShipmentLabel shipmentLabel) {
        this.shipmentLabel = shipmentLabel;
    }


    /**
     * Gets the shipmentDetails value for this ProcessedShipment.
     *
     * @return shipmentDetails
     */
    public com.stosz.tms.external.aramex.ProcessedShipmentDetails getShipmentDetails() {
        return shipmentDetails;
    }


    /**
     * Sets the shipmentDetails value for this ProcessedShipment.
     *
     * @param shipmentDetails
     */
    public void setShipmentDetails(com.stosz.tms.external.aramex.ProcessedShipmentDetails shipmentDetails) {
        this.shipmentDetails = shipmentDetails;
    }


    /**
     * Gets the shipmentAttachments value for this ProcessedShipment.
     *
     * @return shipmentAttachments
     */
    public com.stosz.tms.external.aramex.ProcessedShipmentAttachment[] getShipmentAttachments() {
        return shipmentAttachments;
    }


    /**
     * Sets the shipmentAttachments value for this ProcessedShipment.
     *
     * @param shipmentAttachments
     */
    public void setShipmentAttachments(com.stosz.tms.external.aramex.ProcessedShipmentAttachment[] shipmentAttachments) {
        this.shipmentAttachments = shipmentAttachments;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProcessedShipment)) return false;
        ProcessedShipment other = (ProcessedShipment) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.ID==null && other.getID()==null) ||
             (this.ID!=null &&
              this.ID.equals(other.getID()))) &&
            ((this.reference1==null && other.getReference1()==null) ||
             (this.reference1!=null &&
              this.reference1.equals(other.getReference1()))) &&
            ((this.reference2==null && other.getReference2()==null) ||
             (this.reference2!=null &&
              this.reference2.equals(other.getReference2()))) &&
            ((this.reference3==null && other.getReference3()==null) ||
             (this.reference3!=null &&
              this.reference3.equals(other.getReference3()))) &&
            ((this.foreignHAWB==null && other.getForeignHAWB()==null) ||
             (this.foreignHAWB!=null &&
              this.foreignHAWB.equals(other.getForeignHAWB()))) &&
            this.hasErrors == other.isHasErrors() &&
            ((this.notifications==null && other.getNotifications()==null) ||
             (this.notifications!=null &&
              java.util.Arrays.equals(this.notifications, other.getNotifications()))) &&
            ((this.shipmentLabel==null && other.getShipmentLabel()==null) ||
             (this.shipmentLabel!=null &&
              this.shipmentLabel.equals(other.getShipmentLabel()))) &&
            ((this.shipmentDetails==null && other.getShipmentDetails()==null) ||
             (this.shipmentDetails!=null &&
              this.shipmentDetails.equals(other.getShipmentDetails()))) &&
            ((this.shipmentAttachments==null && other.getShipmentAttachments()==null) ||
             (this.shipmentAttachments!=null &&
              java.util.Arrays.equals(this.shipmentAttachments, other.getShipmentAttachments())));
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
        if (getID() != null) {
            _hashCode += getID().hashCode();
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
        if (getForeignHAWB() != null) {
            _hashCode += getForeignHAWB().hashCode();
        }
        _hashCode += (isHasErrors() ? Boolean.TRUE : Boolean.FALSE).hashCode();
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
        if (getShipmentLabel() != null) {
            _hashCode += getShipmentLabel().hashCode();
        }
        if (getShipmentDetails() != null) {
            _hashCode += getShipmentDetails().hashCode();
        }
        if (getShipmentAttachments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getShipmentAttachments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getShipmentAttachments(), i);
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
        new org.apache.axis.description.TypeDesc(ProcessedShipment.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipment"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Reference1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Reference2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Reference3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foreignHAWB");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ForeignHAWB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasErrors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "HasErrors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notifications");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Notifications"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Notification"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Notification"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentLabel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentLabel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentLabel"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentAttachments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentAttachments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentAttachment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentAttachment"));
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
