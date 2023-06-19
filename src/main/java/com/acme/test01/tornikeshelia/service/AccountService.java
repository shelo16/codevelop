package com.acme.test01.tornikeshelia.service;

import com.acme.test01.tornikeshelia.exception.model.AccountNotFoundException;
import com.acme.test01.tornikeshelia.exception.model.WithdrawalAmountTooLargeException;

import java.math.BigDecimal;

public interface AccountService {

    void openSavingsAccount(Long customerNum, BigDecimal amountToDeposit);
    void openCurrentAccount(Long customerNum);
    void withdraw(Long accountId, BigDecimal amountToWithdraw)
            throws AccountNotFoundException, WithdrawalAmountTooLargeException;
    void deposit(Long accountId, BigDecimal amountToDeposit)
            throws AccountNotFoundException;


}
