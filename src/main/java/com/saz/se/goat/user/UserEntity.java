package com.saz.se.goat.user;

import com.saz.se.goat.model.Address;
import com.saz.se.goat.cart.CartEntity;
import com.saz.se.goat.model.Role;
import com.saz.se.goat.order.OrderEntity;
import com.saz.se.goat.product.ProductEntity;
import com.saz.se.goat.utils.RoleListConverter;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity
{

    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", initialValue = 543390, allocationSize = 1)
    private long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phoneNo;
    @Convert(converter = RoleListConverter.class)
    @Column(nullable = false)
    private List<Role> roles;
    @Column(nullable = false)
    private boolean active = false;
    @Column(nullable = false)
    private boolean initialDiscount;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<OrderEntity> orderEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CartEntity> cartEntityList = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_product",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<ProductEntity> favorites = new ArrayList<>();

    public UserEntity() {}

    public UserEntity(String firstName, String lastName, String email, String password, String phoneNo, Address address)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.roles = new ArrayList<>();
        this.roles.add(Role.USER);

        if (email.equals("shakirahmed84@yahoo.com")) {
            this.roles.add(Role.ADMIN); // Add ADMIN role if conditions match
        }

        this.active = false;
        this.initialDiscount = false;
        this.address = address;
        this.cartEntityList = new ArrayList<>();
        this.orderEntityList = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isInitialDiscount() {
        return initialDiscount;
    }

    public void setInitialDiscount(boolean initialDiscount) {
        this.initialDiscount = initialDiscount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderEntity> getOrderEntityList() {
        return orderEntityList;
    }

    public void setOrderEntityList(List<OrderEntity> orderEntityList) {
        this.orderEntityList = orderEntityList;
    }

    public List<CartEntity> getCartEntityList() {
        return cartEntityList;
    }

    public List<ProductEntity> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<ProductEntity> favorites) {
        this.favorites = favorites;
    }

    public void setCartEntityList(List<CartEntity> cartEntityList) {
        this.cartEntityList = cartEntityList;
    }

    public void addCartToUser(CartEntity cartEntity) {
        cartEntityList.add(cartEntity);
        cartEntity.setUserEntity(this);
    }
    public void addOrderToUser(OrderEntity orderEntity) {
        orderEntityList.add(orderEntity);
        orderEntity.setUserEntity(this);
    }

    public void addToFavorite(ProductEntity product)
    {
        favorites.add(product);
    }

    public void removeFromFavorite(ProductEntity product)
    {
        favorites.remove(product);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", roles=" + roles +
                ", active=" + active +
                ", initialDiscount=" + initialDiscount +
                ", address=" + address +
                ", orderEntityList=" + orderEntityList +
                ", cartEntityList=" + cartEntityList +
                ", favorites=" + favorites +
                '}';
    }
}
