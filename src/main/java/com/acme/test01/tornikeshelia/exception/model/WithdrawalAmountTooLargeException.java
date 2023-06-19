package com.acme.test01.tornikeshelia.exception.model;

import com.acme.test01.tornikeshelia.exception.util.AcmeError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawalAmountTooLargeException extends Exception {

    private final AcmeError acmeError;

    public WithdrawalAmountTooLargeException(AcmeError acmeError) {
        super(acmeError.getDescription());
        this.acmeError = acmeError;
    }
}
