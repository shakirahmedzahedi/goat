package com.saz.se.goat.user;

import com.saz.se.goat.cart.CartDTO;
import com.saz.se.goat.cart.CartEntity;
import com.saz.se.goat.model.Address;
import com.saz.se.goat.model.AddressDTO;
import com.saz.se.goat.model.Role;
import com.saz.se.goat.order.OrderDTO;
import com.saz.se.goat.order.OrderEntity;
import com.saz.se.goat.utils.RoleListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO
{
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private List<Role> roles;
    private boolean active;
    private boolean initialDiscount;
    private AddressDTO address;
    private List<OrderDTO> orders;
    private List<CartDTO> carts;

    public UserDTO(long id, String firstName, String lastName, String email, String phoneNo,
                   List<Role> roles, boolean active, boolean initialDiscount, AddressDTO addressDTO) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.roles = roles;
        this.active = active;
        this.initialDiscount = initialDiscount;
        this.address = addressDTO;
    }

    public UserDTO(long id, String firstName, String lastName, String email, String phoneNo, List<Role> roles, boolean active, boolean initialDiscount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.roles = roles;
        this.active = active;
        this.initialDiscount = initialDiscount;
    }
}
