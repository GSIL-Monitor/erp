/**
 * ProcessedPickup.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class ProcessedPickup  implements java.io.Serializable {
    private java.lang.String ID;

    private java.lang.String GUID;

    private java.lang.String reference1;

    private java.lang.String reference2;

    private com.stosz.tms.external.aramex.ProcessedShipment[] processedShipments;

    public ProcessedPickup() {
    }

    public ProcessedPickup(
           java.lang.String ID,
           java.lang.String GUID,
           java.lang.String reference1,
           java.lang.String reference2,
           com.stosz.tms.external.aramex.ProcessedShipment[] processedShipments) {
           this.ID = ID;
           this.GUID = GUID;
           this.reference1 = reference1;
           this.reference2 = reference2;
           this.processedShipments = processedShipments;
    }


    /**
     * Gets the ID value for this ProcessedPickup.
     *
     * @return ID
     */
    public java.lang.String getID() {
        return ID;
    }


    /**
     * Sets the ID value for this ProcessedPickup.
     *
     * @param ID
     */
    public void setID(java.lang.String ID) {
        this.ID = ID;
    }


    /**
     * Gets the GUID value for this ProcessedPickup.
     *
     * @return GUID
     */
    public java.lang.String getGUID() {
        return GUID;
    }


    /**
     * Sets the GUID value for this ProcessedPickup.
     *
     * @param GUID
     */
    public void setGUID(java.lang.String GUID) {
        this.GUID = GUID;
    }


    /**
     * Gets the reference1 value for this ProcessedPickup.
     *
     * @return reference1
     */
    public java.lang.String getReference1() {
        return reference1;
    }


    /**
     * Sets the reference1 value for this ProcessedPickup.
     *
     * @param reference1
     */
    public void setReference1(java.lang.String reference1) {
        this.reference1 = reference1;
    }


    /**
     * Gets the reference2 value for this ProcessedPickup.
     *
     * @return reference2
     */
    public java.lang.String getReference2() {
        return reference2;
    }


    /**
     * Sets the reference2 value for this ProcessedPickup.
     *
     * @param reference2
     */
    public void setReference2(java.lang.String reference2) {
        this.reference2 = reference2;
    }


    /**
     * Gets the processedShipments value for this ProcessedPickup.
     *
     * @return processedShipments
     */
    public com.stosz.tms.external.aramex.ProcessedShipment[] getProcessedShipments() {
        return processedShipments;
    }


    /**
     * Sets the processedShipments value for this ProcessedPickup.
     *
     * @param processedShipments
     */
    public void setProcessedShipments(com.stosz.tms.external.aramex.ProcessedShipment[] processedShipments) {
        this.processedShipments = processedShipments;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProcessedPickup)) return false;
        ProcessedPickup other = (ProcessedPickup) obj;
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
            ((this.GUID==null && other.getGUID()==null) ||
             (this.GUID!=null &&
              this.GUID.equals(other.getGUID()))) &&
            ((this.reference1==null && other.getReference1()==null) ||
             (this.reference1!=null &&
              this.reference1.equals(other.getReference1()))) &&
            ((this.reference2==null && other.getReference2()==null) ||
             (this.reference2!=null &&
              this.reference2.equals(other.getReference2()))) &&
            ((this.processedShipments==null && other.getProcessedShipments()==null) ||
             (this.processedShipments!=null &&
              java.util.Arrays.equals(this.processedShipments, other.getProcessedShipments())));
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
        if (getGUID() != null) {
            _hashCode += getGUID().hashCode();
        }
        if (getReference1() != null) {
            _hashCode += getReference1().hashCode();
        }
        if (getReference2() != null) {
            _hashCode += getReference2().hashCode();
        }
        if (getProcessedShipments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProcessedShipments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProcessedShipments(), i);
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
        new org.apache.axis.description.TypeDesc(ProcessedPickup.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedPickup"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "GUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
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
        elemField.setFieldName("processedShipments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipment"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ProcessedShipment"));
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
