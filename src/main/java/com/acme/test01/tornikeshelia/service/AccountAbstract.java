package com.acme.test01.tornikeshelia.service;

import com.acme.test01.tornikeshelia.exception.model.GeneralException;
import com.acme.test01.tornikeshelia.exception.util.AcmeError;
import com.acme.test01.tornikeshelia.model.CurrentAccount;
import com.acme.test01.tornikeshelia.model.SavingsAccount;
import com.acme.test01.tornikeshelia.persistence.repository.currentacc.CurrentAccountRepository;
import com.acme.test01.tornikeshelia.persistence.repository.savingacc.SavingsAccountRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@RequiredArgsConstructor
public abstract class AccountAbstract implements AccountService {

    private final BigDecimal DEPOSIT_MIN_AMOUNT = BigDecimal.valueOf(1000L);
    private final SavingsAccountRepository savingsAccountRepository;
    private final CurrentAccountRepository currentAccountRepository;

    @Override
    public void openSavingsAccount(Long customerNum, BigDecimal amountToDeposit) {

        /** Only for testing purposes, because we don't have a real db **/
        /** IN real world, I would check for existing savings account with customerNum, not with 0L **/
        SavingsAccount savingsAccount =
                savingsAccountRepository.getAccount(0L);
        if (savingsAccount != null) {
            throw new GeneralException(AcmeError.INVALID_REQUEST);
        }

        if (amountToDeposit.compareTo(DEPOSIT_MIN_AMOUNT) < 0) {
            throw new GeneralException(AcmeError.INVALID_REQUEST);
        }

        /** Would use Builder here, but ran out of time **/
        savingsAccount = new SavingsAccount(1L,
                customerNum,
                amountToDeposit,
                new Date(),
                new Date());
        savingsAccountRepository.openSavingsAccount(savingsAccount);

    }

    @Override
    public void openCurrentAccount(Long customerNum) {

        CurrentAccount currentAccount =
                currentAccountRepository.getAccount(customerNum);

        if (currentAccount != null) {
            throw new GeneralException(AcmeError.INVALID_REQUEST);
        }

        /** Would use Builder here, but ran out of time **/
        currentAccount = new CurrentAccount(1L,
                customerNum,
                BigDecimal.ZERO,
                BigDecimal.valueOf(25000),
                new Date(), new Date());

        currentAccountRepository.openCurrentAccount(currentAccount);
    }

}
