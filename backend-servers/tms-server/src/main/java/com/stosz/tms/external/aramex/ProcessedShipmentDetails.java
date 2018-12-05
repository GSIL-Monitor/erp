/**
 * ProcessedShipmentDetails.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class ProcessedShipmentDetails  implements java.io.Serializable {
    private java.lang.String origin;

    private java.lang.String destination;

    private com.stosz.tms.external.aramex.Weight chargeableWeight;

    private java.lang.String descriptionOfGoods;

    private java.lang.String goodsOriginCountry;

    private int numberOfPieces;

    private java.lang.String productGroup;

    private java.lang.String productType;

    private java.lang.String paymentType;

    private java.lang.String paymentOptions;

    private com.stosz.tms.external.aramex.Money customsValueAmount;

    private com.stosz.tms.external.aramex.Money cashOnDeliveryAmount;

    private com.stosz.tms.external.aramex.Money insuranceAmount;

    private com.stosz.tms.external.aramex.Money cashAdditionalAmount;

    private com.stosz.tms.external.aramex.Money collectAmount;

    private java.lang.String services;

    public ProcessedShipmentDetails() {
    }

    public ProcessedShipmentDetails(
           java.lang.String origin,
           java.lang.String destination,
           com.stosz.tms.external.aramex.Weight chargeableWeight,
           java.lang.String descriptionOfGoods,
           java.lang.String goodsOriginCountry,
           int numberOfPieces,
           java.lang.String productGroup,
           java.lang.String productType,
           java.lang.String paymentType,
           java.lang.String paymentOptions,
           com.stosz.tms.external.aramex.Money customsValueAmount,
           com.stosz.tms.external.aramex.Money cashOnDeliveryAmount,
           com.stosz.tms.external.aramex.Money insuranceAmount,
           com.stosz.tms.external.aramex.Money cashAdditionalAmount,
           com.stosz.tms.external.aramex.Money collectAmount,
           java.lang.String services) {
           this.origin = origin;
           this.destination = destination;
           this.chargeableWeight = chargeableWeight;
           this.descriptionOfGoods = descriptionOfGoods;
           this.goodsOriginCountry = goodsOriginCountry;
           this.numberOfPieces = numberOfPieces;
           this.productGroup = productGroup;
           this.productType = productType;
           this.paymentType = paymentType;
           this.paymentOptions = paymentOptions;
           this.customsValueAmount = customsValueAmount;
           this.cashOnDeliveryAmount = cashOnDeliveryAmount;
           this.insuranceAmount = insuranceAmount;
           this.cashAdditionalAmount = cashAdditionalAmount;
           this.collectAmount = collectAmount;
           this.services = services;
    }


    /**
     * Gets the origin value for this ProcessedShipmentDetails.
     *
     * @return origin
     */
    public java.lang.String getOrigin() {
        return origin;
    }


    /**
     * Sets the origin value for this ProcessedShipmentDetails.
     *
     * @param origin
     */
    public void setOrigin(java.lang.String origin) {
        this.origin = origin;
    }


    /**
     * Gets the destination value for this ProcessedShipmentDetails.
     *
     * @return destination
     */
    public java.lang.String getDestination() {
        return destination;
    }


    /**
     * Sets the destination value for this ProcessedShipmentDetails.
     *
     * @param destination
     */
    public void setDestination(java.lang.String destination) {
        this.destination = destination;
    }


    /**
     * Gets the chargeableWeight value for this ProcessedShipmentDetails.
     *
     * @return chargeableWeight
     */
    public com.stosz.tms.external.aramex.Weight getChargeableWeight() {
        return chargeableWeight;
    }


    /**
     * Sets the chargeableWeight value for this ProcessedShipmentDetails.
     *
     * @param chargeableWeight
     */
    public void setChargeableWeight(com.stosz.tms.external.aramex.Weight chargeableWeight) {
        this.chargeableWeight = chargeableWeight;
    }


    /**
     * Gets the descriptionOfGoods value for this ProcessedShipmentDetails.
     *
     * @return descriptionOfGoods
     */
    public java.lang.String getDescriptionOfGoods() {
        return descriptionOfGoods;
    }


    /**
     * Sets the descriptionOfGoods value for this ProcessedShipmentDetails.
     *
     * @param descriptionOfGoods
     */
    public void setDescriptionOfGoods(java.lang.String descriptionOfGoods) {
        this.descriptionOfGoods = descriptionOfGoods;
    }


    /**
     * Gets the goodsOriginCountry value for this ProcessedShipmentDetails.
     *
     * @return goodsOriginCountry
     */
    public java.lang.String getGoodsOriginCountry() {
        return goodsOriginCountry;
    }


    /**
     * Sets the goodsOriginCountry value for this ProcessedShipmentDetails.
     *
     * @param goodsOriginCountry
     */
    public void setGoodsOriginCountry(java.lang.String goodsOriginCountry) {
        this.goodsOriginCountry = goodsOriginCountry;
    }


    /**
     * Gets the numberOfPieces value for this ProcessedShipmentDetails.
     *
     * @return numberOfPieces
     */
    public int getNumberOfPieces() {
        return numberOfPieces;
    }


    /**
     * Sets the numberOfPieces value for this ProcessedShipmentDetails.
     *
     * @param numberOfPieces
     */
    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }


    /**
     * Gets the productGroup value for this ProcessedShipmentDetails.
     *
     * @return productGroup
     */
    public java.lang.String getProductGroup() {
        return productGroup;
    }


    /**
     * Sets the productGroup value for this ProcessedShipmentDetails.
     *
     * @param productGroup
     */
    public void setProductGroup(java.lang.String productGroup) {
        this.productGroup = productGroup;
    }


    /**
     * Gets the productType value for this ProcessedShipmentDetails.
     *
     * @return productType
     */
    public java.lang.String getProductType() {
        return productType;
    }


    /**
     * Sets the productType value for this ProcessedShipmentDetails.
     *
     * @param productType
     */
    public void setProductType(java.lang.String productType) {
        this.productType = productType;
    }


    /**
     * Gets the paymentType value for this ProcessedShipmentDetails.
     *
     * @return paymentType
     */
    public java.lang.String getPaymentType() {
        return paymentType;
    }


    /**
     * Sets the paymentType value for this ProcessedShipmentDetails.
     *
     * @param paymentType
     */
    public void setPaymentType(java.lang.String paymentType) {
        this.paymentType = paymentType;
    }


    /**
     * Gets the paymentOptions value for this ProcessedShipmentDetails.
     *
     * @return paymentOptions
     */
    public java.lang.String getPaymentOptions() {
        return paymentOptions;
    }


    /**
     * Sets the paymentOptions value for this ProcessedShipmentDetails.
     *
     * @param paymentOptions
     */
    public void setPaymentOptions(java.lang.String paymentOptions) {
        this.paymentOptions = paymentOptions;
    }


    /**
     * Gets the customsValueAmount value for this ProcessedShipmentDetails.
     *
     * @return customsValueAmount
     */
    public com.stosz.tms.external.aramex.Money getCustomsValueAmount() {
        return customsValueAmount;
    }


    /**
     * Sets the customsValueAmount value for this ProcessedShipmentDetails.
     *
     * @param customsValueAmount
     */
    public void setCustomsValueAmount(com.stosz.tms.external.aramex.Money customsValueAmount) {
        this.customsValueAmount = customsValueAmount;
    }


    /**
     * Gets the cashOnDeliveryAmount value for this ProcessedShipmentDetails.
     *
     * @return cashOnDeliveryAmount
     */
    public com.stosz.tms.external.aramex.Money getCashOnDeliveryAmount() {
        return cashOnDeliveryAmount;
    }


    /**
     * Sets the cashOnDeliveryAmount value for this ProcessedShipmentDetails.
     *
     * @param cashOnDeliveryAmount
     */
    public void setCashOnDeliveryAmount(com.stosz.tms.external.aramex.Money cashOnDeliveryAmount) {
        this.cashOnDeliveryAmount = cashOnDeliveryAmount;
    }


    /**
     * Gets the insuranceAmount value for this ProcessedShipmentDetails.
     *
     * @return insuranceAmount
     */
    public com.stosz.tms.external.aramex.Money getInsuranceAmount() {
        return insuranceAmount;
    }


    /**
     * Sets the insuranceAmount value for this ProcessedShipmentDetails.
     *
     * @param insuranceAmount
     */
    public void setInsuranceAmount(com.stosz.tms.external.aramex.Money insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }


    /**
     * Gets the cashAdditionalAmount value for this ProcessedShipmentDetails.
     *
     * @return cashAdditionalAmount
     */
    public com.stosz.tms.external.aramex.Money getCashAdditionalAmount() {
        return cashAdditionalAmount;
    }


    /**
     * Sets the cashAdditionalAmount value for this ProcessedShipmentDetails.
     *
     * @param cashAdditionalAmount
     */
    public void setCashAdditionalAmount(com.stosz.tms.external.aramex.Money cashAdditionalAmount) {
        this.cashAdditionalAmount = cashAdditionalAmount;
    }


    /**
     * Gets the collectAmount value for this ProcessedShipmentDetails.
     *
     * @return collectAmount
     */
    public com.stosz.tms.external.aramex.Money getCollectAmount() {
        return collectAmount;
    }


    /**
     * Sets the collectAmount value for this ProcessedShipmentDetails.
     *
     * @param collectAmount
     */
    public void setCollectAmount(com.stosz.tms.external.aramex.Money collectAmount) {
        this.collectAmount = collectAmount;
    }


    /**
     * Gets the services value for this ProcessedShipmentDetails.
     *
     * @return services
     */
    public java.lang.String getServices() {
        return services;
    }


    /**
     * Sets the services value for this ProcessedShipmentDetails.
     *
     * @param services
     */
    public void setServices(java.lang.String services) {
        this.services = services;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProcessedShipmentDetails)) return false;
        ProcessedShipmentDetails other = (ProcessedShipmentDetails) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.origin==null && other.getOrigin()==null) ||
             (this.origin!=null &&
              this.origin.equals(other.getOrigin()))) &&
            ((this.destination==null && other.getDestination()==null) ||
             (this.destination!=null &&
              this.destination.equals(other.getDestination()))) &&
            ((this.chargeableWeight==null && other.getChargeableWeight()==null) ||
             (this.chargeableWeight!=null &&
              this.chargeableWeight.equals(other.getChargeableWeight()))) &&
            ((this.descriptionOfGoods==null && other.getDescriptionOfGoods()==null) ||
             (this.descriptionOfGoods!=null &&
              this.descriptionOfGoods.equals(other.getDescriptionOfGoods()))) &&
            ((this.goodsOriginCountry==null && other.getGoodsOriginCountry()==null) ||
             (this.goodsOriginCountry!=null &&
              this.goodsOriginCountry.equals(other.getGoodsOriginCountry()))) &&
            this.numberOfPieces == other.getNumberOfPieces() &&
            ((this.productGroup==null && other.getProductGroup()==null) ||
             (this.productGroup!=null &&
              this.productGroup.equals(other.getProductGroup()))) &&
            ((this.productType==null && other.getProductType()==null) ||
             (this.productType!=null &&
              this.productType.equals(other.getProductType()))) &&
            ((this.paymentType==null && other.getPaymentType()==null) ||
             (this.paymentType!=null &&
              this.paymentType.equals(other.getPaymentType()))) &&
            ((this.paymentOptions==null && other.getPaymentOptions()==null) ||
             (this.paymentOptions!=null &&
              this.paymentOptions.equals(other.getPaymentOptions()))) &&
            ((this.customsValueAmount==null && other.getCustomsValueAmount()==null) ||
             (this.customsValueAmount!=null &&
              this.customsValueAmount.equals(other.getCustomsValueAmount()))) &&
            ((this.cashOnDeliveryAmount==null && other.getCashOnDeliveryAmount()==null) ||
             (this.cashOnDeliveryAmount!=null &&
              this.cashOnDeliveryAmount.equals(other.getCashOnDeliveryAmount()))) &&
            ((this.insuranceAmount==null && other.getInsuranceAmount()==null) ||
             (this.insuranceAmount!=null &&
              this.insuranceAmount.equals(other.getInsuranceAmount()))) &&
            ((this.cashAdditionalAmount==null && other.getCashAdditionalAmount()==null) ||
             (this.cashAdditionalAmount!=null &&
              this.cashAdditionalAmount.equals(other.getCashAdditionalAmount()))) &&
            ((this.collectAmount==null && other.getCollectAmount()==null) ||
             (this.collectAmount!=null &&
              this.collectAmount.equals(other.getCollectAmount()))) &&
            ((this.services==null && other.getServices()==null) ||
             (this.services!=null &&
              this.services.equals(other.getServices())));
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
        if (getOrigin() != null) {
            _hashCode += getOrigin().hashCode();
        }
        if (getDestination() != null) {
            _hashCode += getDestination().hashCode();
        }
        if (getChargeableWeight() != null) {
            _hashCode += getChargeableWeight().hashCode();
        }
        if (getDescriptionOfGoods() != null) {
            _hashCode += getDescriptionOfGoods().hashCode();
        }
        if (getGoodsOriginCountry() != null) {
            _hashCode += getGoodsOriginCountry().hashCode();
        }
        _hashCode += getNumberOfPieces();
        if (getProductGroup() != null) {
            _hashCode += getProductGroup().hashCode();
        }
        if (getProductType() != null) {
            _hashCode += getProductType().hashCode();
        }
        if (getPaymentType() != null) {
            _hashCode += getPaymentType().hashCode();
        }
        if (getPaymentOptions() != null) {
            _hashCode += getPaymentOptions().hashCode();
        }
        if (getCustomsValueAmount() != null) {
            _hashCode += getCustomsValueAmount().hashCode();
        }
        if (getCashOnDeliveryAmount() != null) {
            _hashCode += getCashOnDeliveryAmount().hashCode();
        }
        if (getInsuranceAmount() != null) {
            _hashCode += getInsuranceAmount().hashCode();
        }
        if (getCashAdditionalAmount() != null) {
            _hashCode += getCashAdditionalAmount().hashCode();
        }
        if (getCollectAmount() != null) {
            _hashCode += getCollectAmount().hashCode();
        }
        if (getServices() != null) {
            _hashCode += getServices().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProcessedShipmentDetails.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipmentDetails"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("origin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Origin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Destination"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargeableWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ChargeableWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Weight"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptionOfGoods");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "DescriptionOfGoods"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("goodsOriginCountry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "GoodsOriginCountry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfPieces");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "NumberOfPieces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("paymentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PaymentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PaymentOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customsValueAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CustomsValueAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashOnDeliveryAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CashOnDeliveryAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insuranceAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "InsuranceAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAdditionalAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CashAdditionalAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "CollectAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Money"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("services");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Services"));
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
