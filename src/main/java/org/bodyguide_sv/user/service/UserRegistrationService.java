package org.bodyguide_sv.user.service;

import java.util.UUID;

import org.bodyguide_sv.auth.enums.SocialProvider;
import org.bodyguide_sv.user.dto.OAuth2UserInfo;
import org.bodyguide_sv.user.dto.UserDTO;
import org.bodyguide_sv.user.entity.Users;
import org.bodyguide_sv.user.event.UserRegisterEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final ApplicationEventPublisher eventPublisher;

    private final UserService userService;
    private final UserProfileService userProfileService;
    private final UserMetaService userMetaService;
    private final UserSocialTokenService userSocialTokenService;

    // 유저 정보 로드
    public UserDTO loadUser(OAuth2UserInfo oAuth2UserInfo) {
        SocialProvider provider = oAuth2UserInfo.provider();
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

        return new UserDTO(
                user.getUserId(),
                user.getRole(),
                user.getProvider(),
                user.getProviderId(),
                user.getEmail(),
                user.getName()
            );
    }

    // 회원가입
    @Transactional
    private Users registUser(OAuth2UserInfo oAuth2UserInfo) {
        // 프로필, 메타도 같이 생성 (트랜잭션 처리)
        Users user = oAuth2UserInfo.toEntity();
        user = userService.saveUser(user);
        UUID userId = user.getUserId();

        userProfileService.createUserProfile(userId);
        userMetaService.createUsersMeta(userId);
        userSocialTokenService.createUserProviderToken(userId);

        // 이벤트 발행
        UserRegisterEvent event = new UserRegisterEvent(userId);
        eventPublisher.publishEvent(event);

        return user;
    }

}