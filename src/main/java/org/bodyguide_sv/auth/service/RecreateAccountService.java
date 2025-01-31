package org.bodyguide_sv.auth.service;

import java.util.UUID;

import org.bodyguide_sv.user.service.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecreateAccountService {
    
    private final UserService userService;

    public void recreateAccount(UUID userId) {

        // 기존 user 하드 delete
        userService.hardDeleteUser(userId);

    }

}
