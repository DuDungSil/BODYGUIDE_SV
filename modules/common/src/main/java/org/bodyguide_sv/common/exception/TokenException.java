package org.bodyguide_sv.common.exception;

import org.bodyguide_sv.common.errorHandler.CustomException;
import org.bodyguide_sv.common.errorHandler.ErrorCode;

public class TokenException extends CustomException {

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
