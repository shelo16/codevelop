package com.acme.test01.tornikeshelia.exception.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AcmeError {

    INVALID_REQUEST("Invalid Request", HttpStatus.BAD_REQUEST);


    private final String description;
    private final HttpStatus status;

}
