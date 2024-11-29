package com.saz.se.goat.requestModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressRequest {
    private long userId;
    private String apartmentNo;
    private String houseNo;
    private String postCode;
    private String postOffice;
    private String city;
}
