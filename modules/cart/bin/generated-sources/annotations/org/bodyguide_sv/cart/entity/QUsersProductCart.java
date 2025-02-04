package org.bodyguide_sv.cart.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersProductCart is a Querydsl query type for UsersProductCart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersProductCart extends EntityPathBase<UsersProductCart> {

    private static final long serialVersionUID = 1149116160L;

    public static final QUsersProductCart usersProductCart = new QUsersProductCart("usersProductCart");

    public final NumberPath<Short> cartId = createNumber("cartId", Short.class);

    public final NumberPath<Short> productId = createNumber("productId", Short.class);

    public final NumberPath<Short> quantity = createNumber("quantity", Short.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersProductCart(String variable) {
        super(UsersProductCart.class, forVariable(variable));
    }

    public QUsersProductCart(Path<? extends UsersProductCart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersProductCart(PathMetadata metadata) {
        super(UsersProductCart.class, metadata);
    }

}

