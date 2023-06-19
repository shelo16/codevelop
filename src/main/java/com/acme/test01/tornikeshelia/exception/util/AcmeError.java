package com.acme.test01.tornikeshelia.exception.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AcmeError {

    INVALID_REQUEST("Invalid Request", HttpStatus.BAD_REQUEST),
    INVALID_AMOUNT("Invalid Amount", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_FOUND("Coulnt find account with given parameters", HttpStatus.NOT_FOUND);


    private final String description;
    private final HttpStatus status;

}
