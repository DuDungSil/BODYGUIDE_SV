package org.hepi.hepi_sv.user.service;

import java.util.UUID;

import org.hepi.hepi_sv.activity.service.UserExpProfileService;
import org.hepi.hepi_sv.auth.dto.OAuth2UserInfo;
import org.hepi.hepi_sv.user.entity.Users;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserService userService;
    private final UserProfileService userProfileService;
    private final UserExerciseProfileService userExerciseProfileService;
    private final UserNutritionProfileService userNutritionProfileService;
    private final UserMetaService userMetaService;
    private final UserSocialTokenService userSocialTokenService;
    private final UserExpProfileService userExpProfileService;

    // 유저 정보 로드
    public Users loadUser(OAuth2UserInfo oAuth2UserInfo) {
        String provider = oAuth2UserInfo.provider();
        String providerId = oAuth2UserInfo.providerId();
        String name = oAuth2UserInfo.name();
        String email = oAuth2UserInfo.email();

        // 유저가 존재할 경우 로드, 없으면 회원가입
        Users user = userService.getUserByProvider(provider, providerId);
        user = user != null ? user : registUser(oAuth2UserInfo);

        // 유저 이메일, 이름 검증 후 다를 시 업데이트
        if ( !user.getName().equals(name) || !user.getEmail().equals(email) ) {
            user = userService.updateUserNameAndEmail(user.getUserId(), name, email);
        }

        return user != null ? user : registUser(oAuth2UserInfo);
    }

    // 회원가입
    @Transactional
    private Users registUser(OAuth2UserInfo oAuth2UserInfo) {
        // 프로필, 메타도 같이 생성 (트랜잭션 처리)
        Users user = oAuth2UserInfo.toEntity();
        user = userService.saveUser(user);
        UUID userId = user.getUserId();

        userProfileService.createUserProfile(userId);
        userExerciseProfileService.createUsersExerciseProfile(userId);
        userNutritionProfileService.createUsersNutritionProfile(userId);
        userMetaService.createUsersMeta(userId);
        userSocialTokenService.createUserProviderToken(userId);
        userExpProfileService.createUserExpProfile(userId);

        return user;
    }

    // 유저 삭제
    public void deletionUser() {
        // 메타에 삭제 표시 (구현 필요)
    }
}