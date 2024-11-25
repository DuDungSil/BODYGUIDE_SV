package org.hepi.hepi_sv.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WEB_SHOP_PRODUCT_CLICK_DATA")
public class WebProductClickData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id")
    private Long id;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "record_at")
    private LocalDateTime recordAt;
}