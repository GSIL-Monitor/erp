package com.stosz.olderp.ext.model;


import java.io.Serializable;

/**
 * @author
 * 老erp订单数据中有关于订单联系人
 */
public class OldErpOrderLink implements Serializable {

    private Integer idOrder;

    private Integer idZone;

    /**
     * 订单状态
     */
    private Integer idOrderStatus;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 名字
     */
    private String firstName;

    /**
     * 姓
     */
    private String lastName;

    /**
     * 国家
     */
    private String country;

    /**
     * 电话
     */
    private String tel;

    /**
     * 备用电话
     */
    private String reserveNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 省/洲
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区域
     */
    private String area;

    /**
     * 邮编
     */
    private String zipcode;

    /**
     * 地址
     */
    private String address;

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdZone() {
        return idZone;
    }

    public void setIdZone(Integer idZone) {
        this.idZone = idZone;
    }

    public Integer getIdOrderStatus() {
        return idOrderStatus;
    }

    public void setIdOrderStatus(Integer idOrderStatus) {
        this.idOrderStatus = idOrderStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getReserveNumber() {
        return reserveNumber;
    }

    public void setReserveNumber(String reserveNumber) {
        this.reserveNumber = reserveNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatusName() {
//        OldErpOrderUtil.getOrderType(idOrderStatus).display();
            return "";

    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "OldErpOrderLink{" +
                "idOrder=" + idOrder +
                ", idZone=" + idZone +
                ", idOrderStatus=" + idOrderStatus +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", tel='" + tel + '\'' +
                ", reserveNumber='" + reserveNumber + '\'' +
                ", email='" + email + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}