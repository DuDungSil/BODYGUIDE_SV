package org.bodyguide_sv.cart.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.bodyguide_sv.cart.dto.ProductDto;
import org.bodyguide_sv.cart.entity.QProduct;
import org.bodyguide_sv.cart.entity.QUsersProductCart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class CartQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ProductDto> findCartProducts(UUID userId) {
        QUsersProductCart qUsersProductCart = QUsersProductCart.usersProductCart;
        QProduct qProduct = QProduct.product;

        return queryFactory
                .select(Projections.constructor(
                        ProductDto.class,
                        qUsersProductCart.productId,
                        qProduct.productName,
                        qUsersProductCart.quantity,
                        qProduct.price
                ))
                .from(qUsersProductCart)
                .leftJoin(qProduct).on(qUsersProductCart.productId.eq(qProduct.productId))
                .where(qUsersProductCart.userId.eq(userId))
                .fetch();
    }

}