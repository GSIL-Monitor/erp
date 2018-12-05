/**
 * TrackingResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex.trace;

public class TrackingResult  implements java.io.Serializable {
    private java.lang.String waybillNumber;

    private java.lang.String updateCode;

    private java.lang.String updateDescription;

    private java.util.Calendar updateDateTime;

    private java.lang.String updateLocation;

    private java.lang.String comments;

    private java.lang.String problemCode;

    private java.lang.String grossWeight;

    private java.lang.String chargeableWeight;

    private java.lang.String weightUnit;

    public TrackingResult() {
    }

    public TrackingResult(
           java.lang.String waybillNumber,
           java.lang.String updateCode,
           java.lang.String updateDescription,
           java.util.Calendar updateDateTime,
           java.lang.String updateLocation,
           java.lang.String comments,
           java.lang.String problemCode,
           java.lang.String grossWeight,
           java.lang.String chargeableWeight,
           java.lang.String weightUnit) {
           this.waybillNumber = waybillNumber;
           this.updateCode = updateCode;
           this.updateDescription = updateDescription;
           this.updateDateTime = updateDateTime;
           this.updateLocation = updateLocation;
           this.comments = comments;
           this.problemCode = problemCode;
           this.grossWeight = grossWeight;
           this.chargeableWeight = chargeableWeight;
           this.weightUnit = weightUnit;
    }


    /**
     * Gets the waybillNumber value for this TrackingResult.
     * 
     * @return waybillNumber
     */
    public java.lang.String getWaybillNumber() {
        return waybillNumber;
    }


    /**
     * Sets the waybillNumber value for this TrackingResult.
     * 
     * @param waybillNumber
     */
    public void setWaybillNumber(java.lang.String waybillNumber) {
        this.waybillNumber = waybillNumber;
    }


    /**
     * Gets the updateCode value for this TrackingResult.
     * 
     * @return updateCode
     */
    public java.lang.String getUpdateCode() {
        return updateCode;
    }


    /**
     * Sets the updateCode value for this TrackingResult.
     * 
     * @param updateCode
     */
    public void setUpdateCode(java.lang.String updateCode) {
        this.updateCode = updateCode;
    }


    /**
     * Gets the updateDescription value for this TrackingResult.
     * 
     * @return updateDescription
     */
    public java.lang.String getUpdateDescription() {
        return updateDescription;
    }


    /**
     * Sets the updateDescription value for this TrackingResult.
     * 
     * @param updateDescription
     */
    public void setUpdateDescription(java.lang.String updateDescription) {
        this.updateDescription = updateDescription;
    }


    /**
     * Gets the updateDateTime value for this TrackingResult.
     * 
     * @return updateDateTime
     */
    public java.util.Calendar getUpdateDateTime() {
        return updateDateTime;
    }


    /**
     * Sets the updateDateTime value for this TrackingResult.
     * 
     * @param updateDateTime
     */
    public void setUpdateDateTime(java.util.Calendar updateDateTime) {
        this.updateDateTime = updateDateTime;
    }


    /**
     * Gets the updateLocation value for this TrackingResult.
     * 
     * @return updateLocation
     */
    public java.lang.String getUpdateLocation() {
        return updateLocation;
    }


    /**
     * Sets the updateLocation value for this TrackingResult.
     * 
     * @param updateLocation
     */
    public void setUpdateLocation(java.lang.String updateLocation) {
        this.updateLocation = updateLocation;
    }


    /**
     * Gets the comments value for this TrackingResult.
     * 
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this TrackingResult.
     * 
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the problemCode value for this TrackingResult.
     * 
     * @return problemCode
     */
    public java.lang.String getProblemCode() {
        return problemCode;
    }


    /**
     * Sets the problemCode value for this TrackingResult.
     * 
     * @param problemCode
     */
    public void setProblemCode(java.lang.String problemCode) {
        this.problemCode = problemCode;
    }


    /**
     * Gets the grossWeight value for this TrackingResult.
     * 
     * @return grossWeight
     */
    public java.lang.String getGrossWeight() {
        return grossWeight;
    }


    /**
     * Sets the grossWeight value for this TrackingResult.
     * 
     * @param grossWeight
     */
    public void setGrossWeight(java.lang.String grossWeight) {
        this.grossWeight = grossWeight;
    }


    /**
     * Gets the chargeableWeight value for this TrackingResult.
     * 
     * @return chargeableWeight
     */
    public java.lang.String getChargeableWeight() {
        return chargeableWeight;
    }


    /**
     * Sets the chargeableWeight value for this TrackingResult.
     * 
     * @param chargeableWeight
     */
    public void setChargeableWeight(java.lang.String chargeableWeight) {
        this.chargeableWeight = chargeableWeight;
    }


    /**
     * Gets the weightUnit value for this TrackingResult.
     * 
     * @return weightUnit
     */
    public java.lang.String getWeightUnit() {
        return weightUnit;
    }


    /**
     * Sets the weightUnit value for this TrackingResult.
     * 
     * @param weightUnit
     */
    public void setWeightUnit(java.lang.String weightUnit) {
        this.weightUnit = weightUnit;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TrackingResult)) return false;
        TrackingResult other = (TrackingResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.waybillNumber==null && other.getWaybillNumber()==null) || 
             (this.waybillNumber!=null &&
              this.waybillNumber.equals(other.getWaybillNumber()))) &&
            ((this.updateCode==null && other.getUpdateCode()==null) || 
             (this.updateCode!=null &&
              this.updateCode.equals(other.getUpdateCode()))) &&
            ((this.updateDescription==null && other.getUpdateDescription()==null) || 
             (this.updateDescription!=null &&
              this.updateDescription.equals(other.getUpdateDescription()))) &&
            ((this.updateDateTime==null && other.getUpdateDateTime()==null) || 
             (this.updateDateTime!=null &&
              this.updateDateTime.equals(other.getUpdateDateTime()))) &&
            ((this.updateLocation==null && other.getUpdateLocation()==null) || 
             (this.updateLocation!=null &&
              this.updateLocation.equals(other.getUpdateLocation()))) &&
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.problemCode==null && other.getProblemCode()==null) || 
             (this.problemCode!=null &&
              this.problemCode.equals(other.getProblemCode()))) &&
            ((this.grossWeight==null && other.getGrossWeight()==null) || 
             (this.grossWeight!=null &&
              this.grossWeight.equals(other.getGrossWeight()))) &&
            ((this.chargeableWeight==null && other.getChargeableWeight()==null) || 
             (this.chargeableWeight!=null &&
              this.chargeableWeight.equals(other.getChargeableWeight()))) &&
            ((this.weightUnit==null && other.getWeightUnit()==null) || 
             (this.weightUnit!=null &&
              this.weightUnit.equals(other.getWeightUnit())));
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
        if (getWaybillNumber() != null) {
            _hashCode += getWaybillNumber().hashCode();
        }
        if (getUpdateCode() != null) {
            _hashCode += getUpdateCode().hashCode();
        }
        if (getUpdateDescription() != null) {
            _hashCode += getUpdateDescription().hashCode();
        }
        if (getUpdateDateTime() != null) {
            _hashCode += getUpdateDateTime().hashCode();
        }
        if (getUpdateLocation() != null) {
            _hashCode += getUpdateLocation().hashCode();
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getProblemCode() != null) {
            _hashCode += getProblemCode().hashCode();
        }
        if (getGrossWeight() != null) {
            _hashCode += getGrossWeight().hashCode();
        }
        if (getChargeableWeight() != null) {
            _hashCode += getChargeableWeight().hashCode();
        }
        if (getWeightUnit() != null) {
            _hashCode += getWeightUnit().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TrackingResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "TrackingResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("waybillNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "WaybillNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "UpdateCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "UpdateDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "UpdateDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "UpdateLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("problemCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProblemCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grossWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "GrossWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargeableWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ChargeableWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("weightUnit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "WeightUnit"));
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
