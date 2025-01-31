package org.bodyguide_sv.user.service;

import java.util.UUID;

import org.bodyguide_sv.auth.enums.SocialProvider;
import org.bodyguide_sv.common.errorHandler.ErrorCode;
import org.bodyguide_sv.user.Exception.UserException;
import org.bodyguide_sv.user.dto.OAuth2UserInfo;
import org.bodyguide_sv.user.dto.UserDTO;
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

    // 테스트 유저 검색
    public String getTestUserUUID() {
        String testProviderId = "12345678";
    
        // providerId로 유저 검색
        Users user = usersRepository.findByProviderId(testProviderId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    
        // 유저의 UUID 반환
        return user.getUserId().toString();
    }

    // 유저 정보 가져오기
    public UserDTO getUserById(UUID userId) {

        Users user = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        return user2DTO(user);
    }

    // 프로바이더와 ID로 유저 검색
    public UserDTO getUserByProvider(SocialProvider provider, String providerId) {
        Users user = usersRepository.findByProviderAndProviderId(provider, providerId)
                    .orElse(null); 

        return user != null ? user2DTO(user) : null;
    }

    // 유저 소프트 삭제
    public void deleteUser(UUID userId) {
        Users user = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        user.deleteUser();
        usersRepository.save(user);
    }

    // 유저 하드 삭제 ( 복구 불가 )
    public void hardDeleteUser(UUID userId) {
        Users user = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        user.hardDeleteUser();
        usersRepository.save(user);
    }

    // 유저 복구
    public UserDTO recoveryUser(UUID userId) {
        Users user = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        // 삭제된 유저가 맞는지 확인

        user.recoveryUser();

        usersRepository.save(user);

        return user2DTO(user);
    }

    // OAuth2UserInfo -> User 생성 & 저장
    @Transactional
    public UserDTO createUser(OAuth2UserInfo oAuth2UserInfo) {
        Users user = oAuth2UserInfo.toEntity();
        usersRepository.save(user);
        return user2DTO(user);
    }

    // 유저 이름, 이메일 업데이트
    @Transactional
    public UserDTO updateUserNameAndEmail(UUID userId, String name, String email) {
        // 유저 정보 가져오기
        Users user = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        // 이름 및 이메일 업데이트
        user.setName(name);
        user.setEmail(email);

        usersRepository.save(user);

        // 변경된 유저 저장 및 반환
        return user2DTO(user);
    }

    // GUEST -> USER
    @Transactional
    public UserDTO upgradeUserRole(UUID userId) {

        Users user = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        if (user.getRole() != Role.GUEST) {
            throw new IllegalStateException("User role is not GUEST, cannot upgrade role.");
        }

        user.upgradeRole(); // 권한 변경

        usersRepository.save(user);

        return user2DTO(user); // 변경된 유저 저장
    }

    private UserDTO user2DTO(Users user) {
        return new UserDTO(
            user.getUserId(),
            user.getRole(),
            user.getProvider(),
            user.getProviderId(),
            user.getEmail(),
            user.getName(),
            user.getIsDelete());
    }

}