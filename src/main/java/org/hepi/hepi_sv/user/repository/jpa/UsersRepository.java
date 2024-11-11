package org.hepi.hepi_sv.user.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.hepi.hepi_sv.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    // 특정 제공자와 제공자 ID로 Users 객체 반환
    Optional<Users> findByProviderAndProviderId(String provider, String providerId);

}
