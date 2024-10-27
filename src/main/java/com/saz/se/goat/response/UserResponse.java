package com.saz.se.goat.response;

import com.saz.se.goat.model.Address;
import com.saz.se.goat.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse
{
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private List<Role> roles;
    private boolean active;
    private boolean initialDiscount;
    private Address address;
}
