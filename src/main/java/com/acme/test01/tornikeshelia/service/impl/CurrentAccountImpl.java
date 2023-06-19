package com.acme.test01.tornikeshelia.service.impl;

import com.acme.test01.tornikeshelia.exception.model.AccountNotFoundException;
import com.acme.test01.tornikeshelia.exception.model.GeneralException;
import com.acme.test01.tornikeshelia.exception.model.WithdrawalAmountTooLargeException;
import com.acme.test01.tornikeshelia.exception.util.AcmeError;
import com.acme.test01.tornikeshelia.model.CurrentAccount;
import com.acme.test01.tornikeshelia.persistence.repository.currentacc.CurrentAccountRepository;
import com.acme.test01.tornikeshelia.service.AccountAbstract;

import java.math.BigDecimal;

public class CurrentAccountImpl extends AccountAbstract {
    private final CurrentAccountRepository currentAccountRepository;

    public CurrentAccountImpl(CurrentAccountRepository currentAccountRepository) {
        super(null, currentAccountRepository);
        this.currentAccountRepository = currentAccountRepository;
    }

    @Override
    public void withdraw(Long accountId, BigDecimal amountToWithdraw) throws AccountNotFoundException, WithdrawalAmountTooLargeException {
        CurrentAccount currentAccount =
                currentAccountRepository.getAccount(accountId);
        if (currentAccount == null) {
            throw new AccountNotFoundException(AcmeError.ACCOUNT_NOT_FOUND);
        }

        BigDecimal currentBalance = currentAccount.getBalance();
        BigDecimal overdraftLimit = currentAccount.getOverDraftLimit();
        BigDecimal maxWithdrawalAmount = currentBalance.add(overdraftLimit);

        /** This would be done with parameter validation with Spring Validator **/
        if (amountToWithdraw.compareTo(maxWithdrawalAmount) > 0) {
            throw new WithdrawalAmountTooLargeException(AcmeError.INVALID_AMOUNT);
        }

        BigDecimal updatedBalance = currentBalance.subtract(amountToWithdraw);
        currentAccount.setBalance(updatedBalance);
        currentAccountRepository.save(currentAccount);

    }

    @Override
    public void deposit(Long accountId, BigDecimal amountToDeposit) throws AccountNotFoundException {

        CurrentAccount currentAccount =
                currentAccountRepository.getAccount(accountId);
        if (currentAccount == null) {
            throw new AccountNotFoundException(AcmeError.ACCOUNT_NOT_FOUND);
        }

        /** This would be done with Parameter validation in Spring validator **/
        if (amountToDeposit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new GeneralException(AcmeError.INVALID_AMOUNT);
        }

        // Perform the deposit logic
        BigDecimal currentBalance = currentAccount.getBalance();
        BigDecimal updatedBalance = currentBalance.add(amountToDeposit);
        currentAccount.setBalance(updatedBalance);
        currentAccountRepository.save(currentAccount);
    }
}
