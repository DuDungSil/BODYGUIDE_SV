package org.bodyguide_sv.user.service;

import java.util.UUID;

import org.bodyguide_sv.common.errorHandler.ErrorCode;
import org.bodyguide_sv.user.Exception.UserException;
import org.bodyguide_sv.user.entity.Users;
import org.bodyguide_sv.user.enums.Role;
import org.bodyguide_sv.user.repository.UsersRepository;
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

    // 유저 이름, 이메일 업데이트
    @Transactional
    public Users updateUserNameAndEmail(UUID userId, String name, String email) {
        // 유저 정보 가져오기
        Users user = getUserById(userId);

        // 이름 및 이메일 업데이트
        user.setName(name);
        user.setEmail(email);

        // 변경된 유저 저장 및 반환
        return saveUser(user);
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