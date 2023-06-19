package com.acme.test01.tornikeshelia.exception.model;

import com.acme.test01.tornikeshelia.exception.util.AcmeError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GeneralException extends RuntimeException {

    private final AcmeError acmeError;
}
