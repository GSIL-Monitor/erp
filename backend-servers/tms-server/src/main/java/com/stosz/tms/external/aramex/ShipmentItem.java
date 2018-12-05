/**
 * ShipmentItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class ShipmentItem  implements java.io.Serializable {
    private java.lang.String packageType;

    private int quantity;

    private com.stosz.tms.external.aramex.Weight weight;

    private java.lang.String comments;

    private java.lang.String reference;

    private com.stosz.tms.external.aramex.Dimensions[] piecesDimensions;

    private java.lang.String commodityCode;

    private java.lang.String goodsDescription;

    private java.lang.String countryOfOrigin;

    private com.stosz.tms.external.aramex.Money customsValue;

    private java.lang.String containerNumber;

    public ShipmentItem() {
    }

    public ShipmentItem(
           java.lang.String packageType,
           int quantity,
           com.stosz.tms.external.aramex.Weight weight,
           java.lang.String comments,
           java.lang.String reference,
           com.stosz.tms.external.aramex.Dimensions[] piecesDimensions,
           java.lang.String commodityCode,
           java.lang.String goodsDescription,
           java.lang.String countryOfOrigin,
           com.stosz.tms.external.aramex.Money customsValue,
           java.lang.String containerNumber) {
           this.packageType = packageType;
           this.quantity = quantity;
           this.weight = weight;
           this.comments = comments;
           this.reference = reference;
           this.piecesDimensions = piecesDimensions;
           this.commodityCode = commodityCode;
           this.goodsDescription = goodsDescription;
           this.countryOfOrigin = countryOfOrigin;
           this.customsValue = customsValue;
           this.containerNumber = containerNumber;
    }


    /**
     * Gets the packageType value for this ShipmentItem.
     *
     * @return packageType
     */
    public java.lang.String getPackageType() {
        return packageType;
    }


    /**
     * Sets the packageType value for this ShipmentItem.
     *
     * @param packageType
     */
    public void setPackageType(java.lang.String packageType) {
        this.packageType = packageType;
    }


    /**
     * Gets the quantity value for this ShipmentItem.
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this ShipmentItem.
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the weight value for this ShipmentItem.
     *
     * @return weight
     */
    public com.stosz.tms.external.aramex.Weight getWeight() {
        return weight;
    }


    /**
     * Sets the weight value for this ShipmentItem.
     *
     * @param weight
     */
    public void setWeight(com.stosz.tms.external.aramex.Weight weight) {
        this.weight = weight;
    }


    /**
     * Gets the comments value for this ShipmentItem.
     *
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this ShipmentItem.
     *
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the reference value for this ShipmentItem.
     *
     * @return reference
     */
    public java.lang.String getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this ShipmentItem.
     *
     * @param reference
     */
    public void setReference(java.lang.String reference) {
        this.reference = reference;
    }


    /**
     * Gets the piecesDimensions value for this ShipmentItem.
     *
     * @return piecesDimensions
     */
    public com.stosz.tms.external.aramex.Dimensions[] getPiecesDimensions() {
        return piecesDimensions;
    }


    /**
     * Sets the piecesDimensions value for this ShipmentItem.
     *
     * @param piecesDimensions
     */
    public void setPiecesDimensions(com.stosz.tms.external.aramex.Dimensions[] piecesDimensions) {
        this.piecesDimensions = piecesDimensions;
    }


    /**
     * Gets the commodityCode value for this ShipmentItem.
     *
     * @return commodityCode
     */
    public java.lang.String getCommodityCode() {
        return commodityCode;
    }


    /**
     * Sets the commodityCode value for this ShipmentItem.
     *
     * @param commodityCode
     */
    public void setCommodityCode(java.lang.String commodityCode) {
        this.commodityCode = commodityCode;
    }


    /**
     * Gets the goodsDescription value for this ShipmentItem.
     *
     * @return goodsDescription
     */
    public java.lang.String getGoodsDescription() {
        return goodsDescription;
    }


    /**
     * Sets the goodsDescription value for this ShipmentItem.
     *
     * @param goodsDescription
     */
    public void setGoodsDescription(java.lang.String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }


    /**
     * Gets the countryOfOrigin value for this ShipmentItem.
     *
     * @return countryOfOrigin
     */
    public java.lang.String getCountryOfOrigin() {
        return countryOfOrigin;
    }


    /**
     * Sets the countryOfOrigin value for this ShipmentItem.
     *
     * @param countryOfOrigin
     */
    public void setCountryOfOrigin(java.lang.String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }


    /**
     * Gets the customsValue value for this ShipmentItem.
     *
     * @return customsValue
     */
    public com.stosz.tms.external.aramex.Money getCustomsValue() {
        return customsValue;
    }


    /**
     * Sets the customsValue value for this ShipmentItem.
     *
     * @param customsValue
     */
    public void setCustomsValue(com.stosz.tms.external.aramex.Money customsValue) {
        this.customsValue = customsValue;
    }


    /**
     * Gets the containerNumber value for this ShipmentItem.
     *
     * @return containerNumber
     */
    public java.lang.String getContainerNumber() {
        return containerNumber;
    }


    /**
     * Sets the containerNumber value for this ShipmentItem.
     *
     * @param containerNumber
     */
    public void setContainerNumber(java.lang.String containerNumber) {
        this.containerNumber = containerNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentItem)) return false;
        ShipmentItem other = (ShipmentItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.packageType==null && other.getPackageType()==null) ||
             (this.packageType!=null &&
              this.packageType.equals(other.getPackageType()))) &&
            this.quantity == other.getQuantity() &&
            ((this.weight==null && other.getWeight()==null) ||
             (this.weight!=null &&
              this.weight.equals(other.getWeight()))) &&
            ((this.comments==null && other.getComments()==null) ||
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.reference==null && other.getReference()==null) ||
             (this.reference!=null &&
              this.reference.equals(other.getReference()))) &&
            ((this.piecesDimensions==null && other.getPiecesDimensions()==null) ||
             (this.piecesDimensions!=null &&
              java.util.Arrays.equals(this.piecesDimensions, other.getPiecesDimensions()))) &&
            ((this.commodityCode==null && other.getCommodityCode()==null) ||
             (this.commodityCode!=null &&
              this.commodityCode.equals(other.getCommodityCode()))) &&
            ((this.goodsDescription==null && other.getGoodsDescription()==null) ||
             (this.goodsDescription!=null &&
              this.goodsDescription.equals(other.getGoodsDescription()))) &&
            ((this.countryOfOrigin==null && other.getCountryOfOrigin()==null) ||
             (this.countryOfOrigin!=null &&
              this.countryOfOrigin.equals(other.getCountryOfOrigin()))) &&
            ((this.customsValue==null && other.getCustomsValue()==null) ||
             (this.customsValue!=null &&
              this.customsValue.equals(other.getCustomsValue()))) &&
            ((this.containerNumber==null && other.getContainerNumber()==null) ||
             (this.containerNumber!=null &&
              this.containerNumber.equals(other.getContainerNumber())));
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
        if (getPackageType() != null) {
            _hashCode += getPackageType().hashCode();
        }
        _hashCode += getQuantity();
        if (getWeight() != null) {
            _hashCode += getWeight().hashCode();
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        if (getPiecesDimensions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPiecesDimensions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPiecesDimensions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCommodityCode() != null) {
            _hashCode += getCommodityCode().hashCode();
        }
        if (getGoodsDescription() != null) {
            _hashCode += getGoodsDescription().hashCode();
        }
        if (getCountryOfOrigin() != null) {
            _hashCode += getCountryOfOrigin().hashCode();
        }
        if (getCustomsValue() != null) {
            _hashCode += getCustomsValue().hashCode();
        }
        if (getContainerNumber() != null) {
            _hashCode += getContainerNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ShipmentItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PackageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Quantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("weight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Weight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Weight"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Comments"));
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
        elemField.setFieldName("piecesDimensions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PiecesDimensions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Dimensions"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Dimensions"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commodityCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CommodityCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("goodsDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "GoodsDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryOfOrigin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CountryOfOrigin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customsValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CustomsValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("containerNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ContainerNumber"));
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
