package org.bodyguide_sv.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "USERS_PRODUCT_CART")
public class UsersProductCart {

    @Id
    @Column(name = "cart_id")
    private short cartId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "product_id")
    private short productId;

    @Column(name = "quantity")
    private short quantity;

}
