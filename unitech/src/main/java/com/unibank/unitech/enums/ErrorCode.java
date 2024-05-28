package com.unibank.unitech.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {


    INTERNAL_SERVER_ERROR("500", "Internal server error!"),
    BAD_REQUEST("400", "Bad request!"),
    VALIDATION_ERROR("400", "Validation error!"),
    ILLEGAL_ARGUMENT_EXCEPTION("400", "Illegal argument exception"),
    USER_NOT_FOUND("404", "User not found with given pin"),
    UNAUTHORIZED("401", "UNAUTHORIZED"),
    MONEY_SENDER("404", "Money sender account not found or Deactivated with given account number"),
    MONEY_RECEIVER("404", "Money receiver account not found  or Deactivated with given account number"),
    SAME_ACCOUNT("400", "Same account selected for transfer"),
    INSUFFICIENT_BALANCE("400", "Insufficient balance of sender account"),
    USER_EXIST("409", "User already exist with given pin"),
    SQL_ERROR("409", "Duplicate entry for unique constraint");


    private final String code;
    private final String message;


}
