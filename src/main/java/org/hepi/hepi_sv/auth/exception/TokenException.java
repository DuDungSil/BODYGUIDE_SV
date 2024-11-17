package org.hepi.hepi_sv.auth.exception;

import org.hepi.hepi_sv.common.errorHandler.CustomException;
import org.hepi.hepi_sv.common.errorHandler.ErrorCode;

public class TokenException extends CustomException {

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
