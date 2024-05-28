package com.unibank.unitech.exception;

import java.io.Serial;

public class InsufficientBalanceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4328743;

    public InsufficientBalanceException() {
        super();
    }

}
