package com.unibank.unitech.exception;

import com.unibank.unitech.enums.ErrorCode;
import com.unibank.unitech.exception.base.NotFoundException;

public class UserNotFoundWithGivenPin extends NotFoundException {

    public UserNotFoundWithGivenPin(String... args) {
        super(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMessage(), args);
    }

}
