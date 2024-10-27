package com.saz.se.goat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO
{
    private String apartmentNo;
    private String houseNo;
    private String postCode;
    private String postOffice;
    private String city;
}
