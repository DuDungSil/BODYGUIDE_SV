package org.bodyguide_sv.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bodyguide_sv.cart.dto.ProductDto;
import org.bodyguide_sv.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "Cart", description = "장바구니 관련")
public class CartController {

    private final CartService cartService;

    // 장바구니 상품 조회
    @GetMapping("")
    @Operation(summary = "장바구니 상품 조회", description = "장바구니에 담겨있는 상품들의 목록과 수량을 조회")
    public ResponseEntity<List<ProductDto>> getMemoDays(@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        List<ProductDto> products = cartService.getCartProducts(userId);
        return ResponseEntity.ok(products);
    }

}
