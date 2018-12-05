/**
 * Attachment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class Attachment  implements java.io.Serializable {
    private java.lang.String fileName;

    private java.lang.String fileExtension;

    private byte[] fileContents;

    public Attachment() {
    }

    public Attachment(
           java.lang.String fileName,
           java.lang.String fileExtension,
           byte[] fileContents) {
           this.fileName = fileName;
           this.fileExtension = fileExtension;
           this.fileContents = fileContents;
    }


    /**
     * Gets the fileName value for this Attachment.
     *
     * @return fileName
     */
    public java.lang.String getFileName() {
        return fileName;
    }


    /**
     * Sets the fileName value for this Attachment.
     *
     * @param fileName
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }


    /**
     * Gets the fileExtension value for this Attachment.
     *
     * @return fileExtension
     */
    public java.lang.String getFileExtension() {
        return fileExtension;
    }


    /**
     * Sets the fileExtension value for this Attachment.
     *
     * @param fileExtension
     */
    public void setFileExtension(java.lang.String fileExtension) {
        this.fileExtension = fileExtension;
    }


    /**
     * Gets the fileContents value for this Attachment.
     *
     * @return fileContents
     */
    public byte[] getFileContents() {
        return fileContents;
    }


    /**
     * Sets the fileContents value for this Attachment.
     *
     * @param fileContents
     */
    public void setFileContents(byte[] fileContents) {
        this.fileContents = fileContents;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Attachment)) return false;
        Attachment other = (Attachment) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.fileName==null && other.getFileName()==null) ||
             (this.fileName!=null &&
              this.fileName.equals(other.getFileName()))) &&
            ((this.fileExtension==null && other.getFileExtension()==null) ||
             (this.fileExtension!=null &&
              this.fileExtension.equals(other.getFileExtension()))) &&
            ((this.fileContents==null && other.getFileContents()==null) ||
             (this.fileContents!=null &&
              java.util.Arrays.equals(this.fileContents, other.getFileContents())));
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
        if (getFileName() != null) {
            _hashCode += getFileName().hashCode();
        }
        if (getFileExtension() != null) {
            _hashCode += getFileExtension().hashCode();
        }
        if (getFileContents() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileContents());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFileContents(), i);
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
        new org.apache.axis.description.TypeDesc(Attachment.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Attachment"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "FileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileExtension");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "FileExtension"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileContents");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "FileContents"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
