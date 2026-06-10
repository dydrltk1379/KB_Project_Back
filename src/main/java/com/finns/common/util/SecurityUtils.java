package com.finns.common.util;

import com.finns.common.exception.ForbiddenAccessException;
import com.finns.security.account.domain.CustomUser;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static void assertSameUser(Long userNo, CustomUser customUser) {
        if (customUser == null || customUser.getMember() == null) {
            throw new AuthenticationCredentialsNotFoundException("로그인이 필요합니다.");
        }
        if (customUser.getMember().getUser_no() != userNo.intValue()) {
            throw new ForbiddenAccessException("본인의 데이터만 조회할 수 있습니다.");
        }
    }
}
