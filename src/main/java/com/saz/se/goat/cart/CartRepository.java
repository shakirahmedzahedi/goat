package com.saz.se.goat.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long>
{
    @Query("SELECT c FROM CartEntity c WHERE c.userEntity.id = :userId AND c.active = true")
    Optional<CartEntity> findActiveCartByUserId(@Param("userId") Long userId);
}
