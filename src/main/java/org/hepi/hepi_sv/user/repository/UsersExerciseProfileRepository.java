package org.hepi.hepi_sv.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.hepi.hepi_sv.user.entity.UsersExerciseProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExerciseProfileRepository extends JpaRepository<UsersExerciseProfile, Long> {

    Optional<UsersExerciseProfile> findByUserId(UUID userId);

}