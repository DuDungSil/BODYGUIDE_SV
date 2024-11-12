package org.hepi.hepi_sv.user.Exception;

import org.hepi.hepi_sv.common.errorHandler.CustomErrorCode;
import org.hepi.hepi_sv.common.errorHandler.CustomException;

public class UserException extends CustomException {

    public UserException(CustomErrorCode errorCode) {
        super(errorCode);
    }
}
