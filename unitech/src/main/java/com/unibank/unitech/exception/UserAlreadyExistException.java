package com.unibank.unitech.exception;

import java.io.Serial;

public class UserAlreadyExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4328743;

    public UserAlreadyExistException() {
        super();
    }

}