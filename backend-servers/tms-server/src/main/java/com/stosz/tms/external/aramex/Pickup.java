/**
 * Pickup.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.stosz.tms.external.aramex;

public class Pickup  implements java.io.Serializable {
    private com.stosz.tms.external.aramex.Address pickupAddress;

    private com.stosz.tms.external.aramex.Contact pickupContact;

    private java.lang.String pickupLocation;

    private java.util.Calendar pickupDate;

    private java.util.Calendar readyTime;

    private java.util.Calendar lastPickupTime;

    private java.util.Calendar closingTime;

    private java.lang.String comments;

    private java.lang.String reference1;

    private java.lang.String reference2;

    private java.lang.String vehicle;

    private com.stosz.tms.external.aramex.Shipment[] shipments;

    private com.stosz.tms.external.aramex.PickupItemDetail[] pickupItems;

    private java.lang.String status;

    private com.stosz.tms.external.aramex.ExistingShipment[] existingShipments;

    private java.lang.String branch;

    private java.lang.String routeCode;

    public Pickup() {
    }

    public Pickup(
            com.stosz.tms.external.aramex.Address pickupAddress,
            com.stosz.tms.external.aramex.Contact pickupContact,
            java.lang.String pickupLocation,
            java.util.Calendar pickupDate,
            java.util.Calendar readyTime,
            java.util.Calendar lastPickupTime,
            java.util.Calendar closingTime,
            java.lang.String comments,
            java.lang.String reference1,
            java.lang.String reference2,
            java.lang.String vehicle,
            com.stosz.tms.external.aramex.Shipment[] shipments,
            com.stosz.tms.external.aramex.PickupItemDetail[] pickupItems,
            java.lang.String status,
            com.stosz.tms.external.aramex.ExistingShipment[] existingShipments,
            java.lang.String branch,
            java.lang.String routeCode) {
        this.pickupAddress = pickupAddress;
        this.pickupContact = pickupContact;
        this.pickupLocation = pickupLocation;
        this.pickupDate = pickupDate;
        this.readyTime = readyTime;
        this.lastPickupTime = lastPickupTime;
        this.closingTime = closingTime;
        this.comments = comments;
        this.reference1 = reference1;
        this.reference2 = reference2;
        this.vehicle = vehicle;
        this.shipments = shipments;
        this.pickupItems = pickupItems;
        this.status = status;
        this.existingShipments = existingShipments;
        this.branch = branch;
        this.routeCode = routeCode;
    }


    /**
     * Gets the pickupAddress value for this Pickup.
     *
     * @return pickupAddress
     */
    public com.stosz.tms.external.aramex.Address getPickupAddress() {
        return pickupAddress;
    }


    /**
     * Sets the pickupAddress value for this Pickup.
     *
     * @param pickupAddress
     */
    public void setPickupAddress(com.stosz.tms.external.aramex.Address pickupAddress) {
        this.pickupAddress = pickupAddress;
    }


    /**
     * Gets the pickupContact value for this Pickup.
     *
     * @return pickupContact
     */
    public com.stosz.tms.external.aramex.Contact getPickupContact() {
        return pickupContact;
    }


    /**
     * Sets the pickupContact value for this Pickup.
     *
     * @param pickupContact
     */
    public void setPickupContact(com.stosz.tms.external.aramex.Contact pickupContact) {
        this.pickupContact = pickupContact;
    }


    /**
     * Gets the pickupLocation value for this Pickup.
     *
     * @return pickupLocation
     */
    public java.lang.String getPickupLocation() {
        return pickupLocation;
    }


    /**
     * Sets the pickupLocation value for this Pickup.
     *
     * @param pickupLocation
     */
    public void setPickupLocation(java.lang.String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }


    /**
     * Gets the pickupDate value for this Pickup.
     *
     * @return pickupDate
     */
    public java.util.Calendar getPickupDate() {
        return pickupDate;
    }


    /**
     * Sets the pickupDate value for this Pickup.
     *
     * @param pickupDate
     */
    public void setPickupDate(java.util.Calendar pickupDate) {
        this.pickupDate = pickupDate;
    }


    /**
     * Gets the readyTime value for this Pickup.
     *
     * @return readyTime
     */
    public java.util.Calendar getReadyTime() {
        return readyTime;
    }


    /**
     * Sets the readyTime value for this Pickup.
     *
     * @param readyTime
     */
    public void setReadyTime(java.util.Calendar readyTime) {
        this.readyTime = readyTime;
    }


    /**
     * Gets the lastPickupTime value for this Pickup.
     *
     * @return lastPickupTime
     */
    public java.util.Calendar getLastPickupTime() {
        return lastPickupTime;
    }


    /**
     * Sets the lastPickupTime value for this Pickup.
     *
     * @param lastPickupTime
     */
    public void setLastPickupTime(java.util.Calendar lastPickupTime) {
        this.lastPickupTime = lastPickupTime;
    }


    /**
     * Gets the closingTime value for this Pickup.
     *
     * @return closingTime
     */
    public java.util.Calendar getClosingTime() {
        return closingTime;
    }


    /**
     * Sets the closingTime value for this Pickup.
     *
     * @param closingTime
     */
    public void setClosingTime(java.util.Calendar closingTime) {
        this.closingTime = closingTime;
    }


    /**
     * Gets the comments value for this Pickup.
     *
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this Pickup.
     *
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the reference1 value for this Pickup.
     *
     * @return reference1
     */
    public java.lang.String getReference1() {
        return reference1;
    }


    /**
     * Sets the reference1 value for this Pickup.
     *
     * @param reference1
     */
    public void setReference1(java.lang.String reference1) {
        this.reference1 = reference1;
    }


    /**
     * Gets the reference2 value for this Pickup.
     *
     * @return reference2
     */
    public java.lang.String getReference2() {
        return reference2;
    }


    /**
     * Sets the reference2 value for this Pickup.
     *
     * @param reference2
     */
    public void setReference2(java.lang.String reference2) {
        this.reference2 = reference2;
    }


    /**
     * Gets the vehicle value for this Pickup.
     *
     * @return vehicle
     */
    public java.lang.String getVehicle() {
        return vehicle;
    }


    /**
     * Sets the vehicle value for this Pickup.
     *
     * @param vehicle
     */
    public void setVehicle(java.lang.String vehicle) {
        this.vehicle = vehicle;
    }


    /**
     * Gets the shipments value for this Pickup.
     *
     * @return shipments
     */
    public com.stosz.tms.external.aramex.Shipment[] getShipments() {
        return shipments;
    }


    /**
     * Sets the shipments value for this Pickup.
     *
     * @param shipments
     */
    public void setShipments(com.stosz.tms.external.aramex.Shipment[] shipments) {
        this.shipments = shipments;
    }


    /**
     * Gets the pickupItems value for this Pickup.
     *
     * @return pickupItems
     */
    public com.stosz.tms.external.aramex.PickupItemDetail[] getPickupItems() {
        return pickupItems;
    }


    /**
     * Sets the pickupItems value for this Pickup.
     *
     * @param pickupItems
     */
    public void setPickupItems(com.stosz.tms.external.aramex.PickupItemDetail[] pickupItems) {
        this.pickupItems = pickupItems;
    }


    /**
     * Gets the status value for this Pickup.
     *
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Pickup.
     *
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the existingShipments value for this Pickup.
     *
     * @return existingShipments
     */
    public com.stosz.tms.external.aramex.ExistingShipment[] getExistingShipments() {
        return existingShipments;
    }


    /**
     * Sets the existingShipments value for this Pickup.
     *
     * @param existingShipments
     */
    public void setExistingShipments(com.stosz.tms.external.aramex.ExistingShipment[] existingShipments) {
        this.existingShipments = existingShipments;
    }


    /**
     * Gets the branch value for this Pickup.
     *
     * @return branch
     */
    public java.lang.String getBranch() {
        return branch;
    }


    /**
     * Sets the branch value for this Pickup.
     *
     * @param branch
     */
    public void setBranch(java.lang.String branch) {
        this.branch = branch;
    }


    /**
     * Gets the routeCode value for this Pickup.
     *
     * @return routeCode
     */
    public java.lang.String getRouteCode() {
        return routeCode;
    }


    /**
     * Sets the routeCode value for this Pickup.
     *
     * @param routeCode
     */
    public void setRouteCode(java.lang.String routeCode) {
        this.routeCode = routeCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Pickup)) return false;
        Pickup other = (Pickup) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.pickupAddress==null && other.getPickupAddress()==null) ||
                        (this.pickupAddress!=null &&
                                this.pickupAddress.equals(other.getPickupAddress()))) &&
                ((this.pickupContact==null && other.getPickupContact()==null) ||
                        (this.pickupContact!=null &&
                                this.pickupContact.equals(other.getPickupContact()))) &&
                ((this.pickupLocation==null && other.getPickupLocation()==null) ||
                        (this.pickupLocation!=null &&
                                this.pickupLocation.equals(other.getPickupLocation()))) &&
                ((this.pickupDate==null && other.getPickupDate()==null) ||
                        (this.pickupDate!=null &&
                                this.pickupDate.equals(other.getPickupDate()))) &&
                ((this.readyTime==null && other.getReadyTime()==null) ||
                        (this.readyTime!=null &&
                                this.readyTime.equals(other.getReadyTime()))) &&
                ((this.lastPickupTime==null && other.getLastPickupTime()==null) ||
                        (this.lastPickupTime!=null &&
                                this.lastPickupTime.equals(other.getLastPickupTime()))) &&
                ((this.closingTime==null && other.getClosingTime()==null) ||
                        (this.closingTime!=null &&
                                this.closingTime.equals(other.getClosingTime()))) &&
                ((this.comments==null && other.getComments()==null) ||
                        (this.comments!=null &&
                                this.comments.equals(other.getComments()))) &&
                ((this.reference1==null && other.getReference1()==null) ||
                        (this.reference1!=null &&
                                this.reference1.equals(other.getReference1()))) &&
                ((this.reference2==null && other.getReference2()==null) ||
                        (this.reference2!=null &&
                                this.reference2.equals(other.getReference2()))) &&
                ((this.vehicle==null && other.getVehicle()==null) ||
                        (this.vehicle!=null &&
                                this.vehicle.equals(other.getVehicle()))) &&
                ((this.shipments==null && other.getShipments()==null) ||
                        (this.shipments!=null &&
                                java.util.Arrays.equals(this.shipments, other.getShipments()))) &&
                ((this.pickupItems==null && other.getPickupItems()==null) ||
                        (this.pickupItems!=null &&
                                java.util.Arrays.equals(this.pickupItems, other.getPickupItems()))) &&
                ((this.status==null && other.getStatus()==null) ||
                        (this.status!=null &&
                                this.status.equals(other.getStatus()))) &&
                ((this.existingShipments==null && other.getExistingShipments()==null) ||
                        (this.existingShipments!=null &&
                                java.util.Arrays.equals(this.existingShipments, other.getExistingShipments()))) &&
                ((this.branch==null && other.getBranch()==null) ||
                        (this.branch!=null &&
                                this.branch.equals(other.getBranch()))) &&
                ((this.routeCode==null && other.getRouteCode()==null) ||
                        (this.routeCode!=null &&
                                this.routeCode.equals(other.getRouteCode())));
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
        if (getPickupAddress() != null) {
            _hashCode += getPickupAddress().hashCode();
        }
        if (getPickupContact() != null) {
            _hashCode += getPickupContact().hashCode();
        }
        if (getPickupLocation() != null) {
            _hashCode += getPickupLocation().hashCode();
        }
        if (getPickupDate() != null) {
            _hashCode += getPickupDate().hashCode();
        }
        if (getReadyTime() != null) {
            _hashCode += getReadyTime().hashCode();
        }
        if (getLastPickupTime() != null) {
            _hashCode += getLastPickupTime().hashCode();
        }
        if (getClosingTime() != null) {
            _hashCode += getClosingTime().hashCode();
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getReference1() != null) {
            _hashCode += getReference1().hashCode();
        }
        if (getReference2() != null) {
            _hashCode += getReference2().hashCode();
        }
        if (getVehicle() != null) {
            _hashCode += getVehicle().hashCode();
        }
        if (getShipments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getShipments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getShipments(), i);
                if (obj != null &&
                        !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPickupItems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPickupItems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPickupItems(), i);
                if (obj != null &&
                        !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getExistingShipments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExistingShipments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExistingShipments(), i);
                if (obj != null &&
                        !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBranch() != null) {
            _hashCode += getBranch().hashCode();
        }
        if (getRouteCode() != null) {
            _hashCode += getRouteCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(Pickup.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Pickup"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pickupAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Address"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pickupContact");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupContact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Contact"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pickupLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pickupDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("readyTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ReadyTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastPickupTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "LastPickupTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("closingTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ClosingTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("vehicle");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Vehicle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Shipment"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pickupItems");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupItemDetail"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "PickupItemDetail"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existingShipments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ExistingShipments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ExistingShipment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "ExistingShipment"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "Branch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("routeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.aramex.net/ShippingAPI/v1/", "RouteCode"));
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
