package org.bodyguide_sv.user.exception;

import org.bodyguide_sv.common.errorHandler.CustomException;
import org.bodyguide_sv.common.errorHandler.ErrorCode;

public class UserException extends CustomException {

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
