package org.hepi.hepi_sv.user.service;

import java.util.UUID;
import org.hepi.hepi_sv.common.errorHandler.ErrorCode;
import org.hepi.hepi_sv.user.Exception.UserException;
import org.hepi.hepi_sv.user.entity.Role;
import org.hepi.hepi_sv.user.entity.Users;
import org.hepi.hepi_sv.user.repository.UsersRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    // 유저 정보 가져오기
    public Users getUserById(UUID userId) {
        return usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    // 프로바이더와 ID로 유저 검색
    public Users getUserByProvider(String provider, String providerId) {
        return usersRepository.findByProviderAndProviderId(provider, providerId)
                .orElse(null); // Optional 사용하지 않고 null 반환
    }

    // 유저 저장
    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    // GUEST -> USER
    @Transactional
    public Users upgradeUserRole(UUID userID) {
        Users user = getUserById(userID); // 유저 정보 가져오기

        if (user.getRole() != Role.GUEST) {
            throw new IllegalStateException("User role is not GUEST, cannot upgrade role.");
        }

        user.setRole(Role.USER); // 권한 변경
        
        return saveUser(user); // 변경된 유저 저장
    }

}