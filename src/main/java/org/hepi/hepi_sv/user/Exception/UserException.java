package org.hepi.hepi_sv.user.Exception;

import org.hepi.hepi_sv.common.errorHandler.CustomException;
import org.hepi.hepi_sv.common.errorHandler.ErrorCode;

public class UserException extends CustomException {

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
