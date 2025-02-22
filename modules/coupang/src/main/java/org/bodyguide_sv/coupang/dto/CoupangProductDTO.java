package org.bodyguide_sv.coupang.dto;

public record CoupangProductDTO(
    int productId,
    String name,
    String url,
    String imgUrl,
    String brand,
    int price,
    int salePrice,
    int discount,
    double rating,
    int review,
    String category,
    String keyword,
    Boolean isRocketDelivery,
    Boolean isRocketFresh
) {
}
