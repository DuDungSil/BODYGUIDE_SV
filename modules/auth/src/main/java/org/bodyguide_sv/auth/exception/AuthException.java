package org.bodyguide_sv.auth.exception;

import org.bodyguide_sv.common.errorHandler.CustomException;
import org.bodyguide_sv.common.errorHandler.ErrorCode;

public class AuthException extends CustomException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
