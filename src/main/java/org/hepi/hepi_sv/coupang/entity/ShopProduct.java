package org.hepi.hepi_sv.coupang.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SHOP_PRODUCT")
public class ShopProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private int price;

    @Column(name = "sale_price")
    private int salePrice;

    @Column(name = "discount")
    private int discount;

    @Column(name = "rating")
    private double rating;

    @Column(name = "review")
    private int review;

    @Column(name = "category")
    private String category;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "is_rocket_delivery")
    private Boolean isRocketDelivery;

    @Column(name = "is_rocket_fresh")
    private Boolean isRocketFresh;

}
