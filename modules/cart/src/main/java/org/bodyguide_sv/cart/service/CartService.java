package org.bodyguide_sv.cart.service;

import lombok.RequiredArgsConstructor;
import org.bodyguide_sv.cart.dto.ProductDto;
import org.bodyguide_sv.cart.repository.CartQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class CartService {

    private final CartQueryRepository cartQueryRepository;

    public List<ProductDto> getCartProducts(UUID userId) {
        List<ProductDto> list = cartQueryRepository.findCartProducts(userId);
        return list;
    }

}
