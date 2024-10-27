package com.saz.se.goat.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
public class Address {

    private String apartmentNo;
    private String houseNo;
    private String postCode;
    private String postOffice;
    private String city;

    public Address(String apartmentNo, String houseNo, String postCode, String postOffice, String city) {
        this.apartmentNo = apartmentNo;
        this.houseNo = houseNo;
        this.postCode = postCode;
        this.postOffice = postOffice;
        this.city = city;
    }

    public Address() {

    }

    public String getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "apartmentNo='" + apartmentNo + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", postCode='" + postCode + '\'' +
                ", postOffice='" + postOffice + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
