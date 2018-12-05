/**
 * PickupItemDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class PickupItemDetail  implements java.io.Serializable {
    private java.lang.String productGroup;

    private java.lang.String productType;

    private int numberOfShipments;

    private java.lang.String packageType;

    private java.lang.String payment;

    private com.stosz.tms.external.aramex.Weight shipmentWeight;

    private com.stosz.tms.external.aramex.Volume shipmentVolume;

    private int numberOfPieces;

    private com.stosz.tms.external.aramex.Money cashAmount;

    private com.stosz.tms.external.aramex.Money extraCharges;

    private com.stosz.tms.external.aramex.Dimensions shipmentDimensions;

    private java.lang.String comments;

    public PickupItemDetail() {
    }

    public PickupItemDetail(
           java.lang.String productGroup,
           java.lang.String productType,
           int numberOfShipments,
           java.lang.String packageType,
           java.lang.String payment,
           com.stosz.tms.external.aramex.Weight shipmentWeight,
           com.stosz.tms.external.aramex.Volume shipmentVolume,
           int numberOfPieces,
           com.stosz.tms.external.aramex.Money cashAmount,
           com.stosz.tms.external.aramex.Money extraCharges,
           com.stosz.tms.external.aramex.Dimensions shipmentDimensions,
           java.lang.String comments) {
           this.productGroup = productGroup;
           this.productType = productType;
           this.numberOfShipments = numberOfShipments;
           this.packageType = packageType;
           this.payment = payment;
           this.shipmentWeight = shipmentWeight;
           this.shipmentVolume = shipmentVolume;
           this.numberOfPieces = numberOfPieces;
           this.cashAmount = cashAmount;
           this.extraCharges = extraCharges;
           this.shipmentDimensions = shipmentDimensions;
           this.comments = comments;
    }


    /**
     * Gets the productGroup value for this PickupItemDetail.
     *
     * @return productGroup
     */
    public java.lang.String getProductGroup() {
        return productGroup;
    }


    /**
     * Sets the productGroup value for this PickupItemDetail.
     *
     * @param productGroup
     */
    public void setProductGroup(java.lang.String productGroup) {
        this.productGroup = productGroup;
    }


    /**
     * Gets the productType value for this PickupItemDetail.
     *
     * @return productType
     */
    public java.lang.String getProductType() {
        return productType;
    }


    /**
     * Sets the productType value for this PickupItemDetail.
     *
     * @param productType
     */
    public void setProductType(java.lang.String productType) {
        this.productType = productType;
    }


    /**
     * Gets the numberOfShipments value for this PickupItemDetail.
     *
     * @return numberOfShipments
     */
    public int getNumberOfShipments() {
        return numberOfShipments;
    }


    /**
     * Sets the numberOfShipments value for this PickupItemDetail.
     *
     * @param numberOfShipments
     */
    public void setNumberOfShipments(int numberOfShipments) {
        this.numberOfShipments = numberOfShipments;
    }


    /**
     * Gets the packageType value for this PickupItemDetail.
     *
     * @return packageType
     */
    public java.lang.String getPackageType() {
        return packageType;
    }


    /**
     * Sets the packageType value for this PickupItemDetail.
     *
     * @param packageType
     */
    public void setPackageType(java.lang.String packageType) {
        this.packageType = packageType;
    }


    /**
     * Gets the payment value for this PickupItemDetail.
     *
     * @return payment
     */
    public java.lang.String getPayment() {
        return payment;
    }


    /**
     * Sets the payment value for this PickupItemDetail.
     *
     * @param payment
     */
    public void setPayment(java.lang.String payment) {
        this.payment = payment;
    }


    /**
     * Gets the shipmentWeight value for this PickupItemDetail.
     *
     * @return shipmentWeight
     */
    public com.stosz.tms.external.aramex.Weight getShipmentWeight() {
        return shipmentWeight;
    }


    /**
     * Sets the shipmentWeight value for this PickupItemDetail.
     *
     * @param shipmentWeight
     */
    public void setShipmentWeight(com.stosz.tms.external.aramex.Weight shipmentWeight) {
        this.shipmentWeight = shipmentWeight;
    }


    /**
     * Gets the shipmentVolume value for this PickupItemDetail.
     *
     * @return shipmentVolume
     */
    public com.stosz.tms.external.aramex.Volume getShipmentVolume() {
        return shipmentVolume;
    }


    /**
     * Sets the shipmentVolume value for this PickupItemDetail.
     *
     * @param shipmentVolume
     */
    public void setShipmentVolume(com.stosz.tms.external.aramex.Volume shipmentVolume) {
        this.shipmentVolume = shipmentVolume;
    }


    /**
     * Gets the numberOfPieces value for this PickupItemDetail.
     *
     * @return numberOfPieces
     */
    public int getNumberOfPieces() {
        return numberOfPieces;
    }


    /**
     * Sets the numberOfPieces value for this PickupItemDetail.
     *
     * @param numberOfPieces
     */
    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }


    /**
     * Gets the cashAmount value for this PickupItemDetail.
     *
     * @return cashAmount
     */
    public com.stosz.tms.external.aramex.Money getCashAmount() {
        return cashAmount;
    }


    /**
     * Sets the cashAmount value for this PickupItemDetail.
     *
     * @param cashAmount
     */
    public void setCashAmount(com.stosz.tms.external.aramex.Money cashAmount) {
        this.cashAmount = cashAmount;
    }


    /**
     * Gets the extraCharges value for this PickupItemDetail.
     *
     * @return extraCharges
     */
    public com.stosz.tms.external.aramex.Money getExtraCharges() {
        return extraCharges;
    }


    /**
     * Sets the extraCharges value for this PickupItemDetail.
     *
     * @param extraCharges
     */
    public void setExtraCharges(com.stosz.tms.external.aramex.Money extraCharges) {
        this.extraCharges = extraCharges;
    }


    /**
     * Gets the shipmentDimensions value for this PickupItemDetail.
     *
     * @return shipmentDimensions
     */
    public com.stosz.tms.external.aramex.Dimensions getShipmentDimensions() {
        return shipmentDimensions;
    }


    /**
     * Sets the shipmentDimensions value for this PickupItemDetail.
     *
     * @param shipmentDimensions
     */
    public void setShipmentDimensions(com.stosz.tms.external.aramex.Dimensions shipmentDimensions) {
        this.shipmentDimensions = shipmentDimensions;
    }


    /**
     * Gets the comments value for this PickupItemDetail.
     *
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this PickupItemDetail.
     *
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PickupItemDetail)) return false;
        PickupItemDetail other = (PickupItemDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.productGroup==null && other.getProductGroup()==null) ||
             (this.productGroup!=null &&
              this.productGroup.equals(other.getProductGroup()))) &&
            ((this.productType==null && other.getProductType()==null) ||
             (this.productType!=null &&
              this.productType.equals(other.getProductType()))) &&
            this.numberOfShipments == other.getNumberOfShipments() &&
            ((this.packageType==null && other.getPackageType()==null) ||
             (this.packageType!=null &&
              this.packageType.equals(other.getPackageType()))) &&
            ((this.payment==null && other.getPayment()==null) ||
             (this.payment!=null &&
              this.payment.equals(other.getPayment()))) &&
            ((this.shipmentWeight==null && other.getShipmentWeight()==null) ||
             (this.shipmentWeight!=null &&
              this.shipmentWeight.equals(other.getShipmentWeight()))) &&
            ((this.shipmentVolume==null && other.getShipmentVolume()==null) ||
             (this.shipmentVolume!=null &&
              this.shipmentVolume.equals(other.getShipmentVolume()))) &&
            this.numberOfPieces == other.getNumberOfPieces() &&
            ((this.cashAmount==null && other.getCashAmount()==null) ||
             (this.cashAmount!=null &&
              this.cashAmount.equals(other.getCashAmount()))) &&
            ((this.extraCharges==null && other.getExtraCharges()==null) ||
             (this.extraCharges!=null &&
              this.extraCharges.equals(other.getExtraCharges()))) &&
            ((this.shipmentDimensions==null && other.getShipmentDimensions()==null) ||
             (this.shipmentDimensions!=null &&
              this.shipmentDimensions.equals(other.getShipmentDimensions()))) &&
            ((this.comments==null && other.getComments()==null) ||
             (this.comments!=null &&
              this.comments.equals(other.getComments())));
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
        if (getProductGroup() != null) {
            _hashCode += getProductGroup().hashCode();
        }
        if (getProductType() != null) {
            _hashCode += getProductType().hashCode();
        }
        _hashCode += getNumberOfShipments();
        if (getPackageType() != null) {
            _hashCode += getPackageType().hashCode();
        }
        if (getPayment() != null) {
            _hashCode += getPayment().hashCode();
        }
        if (getShipmentWeight() != null) {
            _hashCode += getShipmentWeight().hashCode();
        }
        if (getShipmentVolume() != null) {
            _hashCode += getShipmentVolume().hashCode();
        }
        _hashCode += getNumberOfPieces();
        if (getCashAmount() != null) {
            _hashCode += getCashAmount().hashCode();
        }
        if (getExtraCharges() != null) {
            _hashCode += getExtraCharges().hashCode();
        }
        if (getShipmentDimensions() != null) {
            _hashCode += getShipmentDimensions().hashCode();
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PickupItemDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupItemDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProductGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProductType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfShipments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "NumberOfShipments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PackageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Payment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Weight"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentVolume");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentVolume"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Volume"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfPieces");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "NumberOfPieces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CashAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extraCharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ExtraCharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentDimensions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentDimensions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Dimensions"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
