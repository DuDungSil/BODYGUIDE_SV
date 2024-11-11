package org.hepi.hepi_sv.user.service;

import org.hepi.hepi_sv.auth.dto.OAuth2UserInfo;
import org.hepi.hepi_sv.user.entity.Users;
import org.hepi.hepi_sv.user.repository.jpa.UsersRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UsersRepository userRepository;
    private final UserProfileService userProfileService;
    private final UserMetaService userMetaService;
    
    // 유저 정보 로드
    public Users loadUser(OAuth2UserInfo oAuth2UserInfo) {
        // 유저가 존재하는지 확인
        
        String provider = oAuth2UserInfo.provider();
        String providerId = oAuth2UserInfo.providerId();
    
        // 유저가 존재할 경우 로드, 없으면 회원가입
        return userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> registUser(oAuth2UserInfo));
    }
    
    // 회원가입
    @Transactional
    private Users registUser(OAuth2UserInfo oAuth2UserInfo) {
        // 프로필, 메타도 같이 생성 ( 트랜잭션 처리 )

        Users user = oAuth2UserInfo.toEntity();
        user = userRepository.save(user);

        userProfileService.createUserProfile(user.getUser_id());
        userMetaService.createUsersMeta(user.getUser_id());

        return user;
    }

    // 유저삭제
    private void deletionUser() {
        // 메타에 삭제 표시
    }

}
