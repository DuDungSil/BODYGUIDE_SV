package org.hepi.hepi_sv.auth.exception;

import org.hepi.hepi_sv.common.errorHandler.CustomException;
import org.hepi.hepi_sv.common.errorHandler.ErrorCode;

public class AuthException extends CustomException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
