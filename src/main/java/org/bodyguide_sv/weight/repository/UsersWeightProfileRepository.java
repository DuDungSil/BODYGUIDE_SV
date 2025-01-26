package org.bodyguide_sv.weight.repository;

import java.util.Optional;

import org.bodyguide_sv.weight.entity.UsersWeightProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface UsersWeightProfileRepository extends JpaRepository<UsersWeightProfile, Long> {
    
    Optional<UsersWeightProfile> findByUserId(UUID userId);

}
