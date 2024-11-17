package org.hepi.hepi_sv.common.redis.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RedisHash(value = "jwt", timeToLive = 60 * 60 * 24 * 7)    
public class Token {

    @Id
    private final UUID userID;

    private String refreshToken;

    public Token updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

}
