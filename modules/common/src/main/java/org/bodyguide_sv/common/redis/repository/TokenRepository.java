package org.bodyguide_sv.common.redis.repository;

import java.util.UUID;

import org.bodyguide_sv.common.redis.entity.Token;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends KeyValueRepository<Token, UUID> {

    
}
