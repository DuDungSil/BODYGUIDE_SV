package org.bodyguide_sv.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @Column(name = "product_id")
    private short productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private double price;

}
