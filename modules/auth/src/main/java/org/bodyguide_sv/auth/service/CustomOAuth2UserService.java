package org.bodyguide_sv.auth.service;

import java.util.Map;

import org.bodyguide_sv.auth.dto.PrincipalDetails;
import org.bodyguide_sv.common.enums.SocialProvider;
import org.bodyguide_sv.user.dto.OAuth2UserInfo;
import org.bodyguide_sv.user.dto.UserDTO;
import org.bodyguide_sv.user.service.UserRegistrationService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    
    private final UserRegistrationService userRegistrationService;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 유저 정보(attributes) 가져오기
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
        
        // 2. resistrationId 가져오기 (third-party id)
        String provider = userRequest.getClientRegistration().getRegistrationId();
        SocialProvider socialProvider = SocialProvider.fromCode(provider);

        // 3. userNameAttributeName 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 4. 유저 정보 dto 생성
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(socialProvider, oAuth2UserAttributes);

        // 5. 회원가입 및 로그인
        UserDTO userDTO = userRegistrationService.loadUser(oAuth2UserInfo);

        // 6. OAuth2User로 반환 
        return new PrincipalDetails(userDTO, oAuth2UserAttributes, userNameAttributeName);
    }
    
}
