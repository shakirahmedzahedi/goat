package com.saz.se.goat.requestModel;

import com.saz.se.goat.model.Address;
import com.saz.se.goat.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNo;
    private Address address;
}
