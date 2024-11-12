package org.hepi.hepi_sv.auth.repository.redis;

import java.util.Optional;

import org.hepi.hepi_sv.auth.entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {

    Optional<Token> findByAccessToken(String accessToken);
    
}
