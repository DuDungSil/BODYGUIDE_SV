package org.bodyguide_sv.auth.enums;

public enum SocialProvider {
    
    KAKAO("kakao", false),
    GOOGLE("google", true),
    APPLE("apple", true);

    private final String providerName;
    private final Boolean isSaveRefreshToken;

    SocialProvider(String providerName, Boolean isSaveRefreshToken) {
        this.providerName = providerName;
        this.isSaveRefreshToken = isSaveRefreshToken;
    }

}
