package org.bodyguide_sv.auth.dto;

import static org.bodyguide_sv.common.errorHandler.ErrorCode.ILLEGAL_REGISTRATION_PROVIDER;

import java.util.Map;

import org.bodyguide_sv.auth.enums.SocialProvider;
import org.bodyguide_sv.auth.exception.AuthException;
import org.bodyguide_sv.user.entity.Users;
import org.bodyguide_sv.user.enums.Role;

import lombok.Builder;

// 유저 정보를 각 provider로부터
@Builder
public record OAuth2UserInfo(
    SocialProvider provider,
    String providerId,
    String name,
    String email
) {
    
    public static OAuth2UserInfo of(SocialProvider socialProvider, Map<String, Object> attributes) {
        return switch (socialProvider) { // registration id별로 userInfo 생성
            case GOOGLE -> ofGoogle(socialProvider, attributes);
            case KAKAO -> ofKakao(socialProvider, attributes);
            default -> throw new AuthException(ILLEGAL_REGISTRATION_PROVIDER);
        };
    }
    
    private static OAuth2UserInfo ofGoogle(SocialProvider socialProvider, Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .provider(socialProvider)
                .providerId((String) attributes.get("sub").toString())
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .build();
    }

    private static OAuth2UserInfo ofKakao(SocialProvider socialProvider, Map<String, Object> attributes) {
        // @SuppressWarnings("unchecked")
        // Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        // @SuppressWarnings("unchecked")
        // Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2UserInfo.builder()
                .provider(socialProvider)
                .providerId(attributes.get("id").toString())
                // .name((String) profile.get("nmickname"))
                // .email((String) account.get("email"))
                .name("test")
                .email("test@example.com")
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .provider(provider)
                .providerId(providerId)
                .name(name)
                .email(email)
                .role(Role.GUEST)
                .build();
    }

}
