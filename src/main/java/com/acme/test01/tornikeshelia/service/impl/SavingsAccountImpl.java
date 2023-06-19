package com.acme.test01.tornikeshelia.service.impl;

import com.acme.test01.tornikeshelia.exception.model.AccountNotFoundException;
import com.acme.test01.tornikeshelia.exception.model.GeneralException;
import com.acme.test01.tornikeshelia.exception.model.WithdrawalAmountTooLargeException;
import com.acme.test01.tornikeshelia.exception.util.AcmeError;
import com.acme.test01.tornikeshelia.model.SavingsAccount;
import com.acme.test01.tornikeshelia.persistence.repository.savingacc.SavingsAccountRepository;
import com.acme.test01.tornikeshelia.service.AccountAbstract;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SavingsAccountImpl extends AccountAbstract {
    private final SavingsAccountRepository savingsAccountRepository;

    public SavingsAccountImpl(SavingsAccountRepository savingsAccountRepository) {
        super(savingsAccountRepository, null);
        this.savingsAccountRepository = savingsAccountRepository;
    }

    @Override
    public void withdraw(Long accountId, BigDecimal amountToWithdraw) throws AccountNotFoundException, WithdrawalAmountTooLargeException {
        SavingsAccount savingsAccount = savingsAccountRepository.getAccount(accountId);
        if (savingsAccount == null) {
            throw new AccountNotFoundException(AcmeError.ACCOUNT_NOT_FOUND);
        }

        BigDecimal currentBalance = savingsAccount.getBalance();

        // Check if the withdrawal amount is valid
        /** This would be done with parameter validation by spring validator **/
        if (currentBalance.subtract(amountToWithdraw).compareTo(BigDecimal.valueOf(1000L)) < 0) {
            throw new WithdrawalAmountTooLargeException(AcmeError.INVALID_AMOUNT);
        }

        // Perform the withdrawal logic
        BigDecimal updatedBalance = currentBalance.subtract(amountToWithdraw);
        savingsAccount.setBalance(updatedBalance);
        savingsAccountRepository.save(savingsAccount);
    }

    @Override
    public void deposit(Long accountId, BigDecimal amountToDeposit) throws AccountNotFoundException {
        SavingsAccount savingsAccount = savingsAccountRepository.getAccount(accountId);

        if (savingsAccount == null) {
            throw new AccountNotFoundException(AcmeError.ACCOUNT_NOT_FOUND);
        }

        /** This would be done with parameter validations by spring validator **/
        if (amountToDeposit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new GeneralException(AcmeError.INVALID_AMOUNT);
        }

        // Perform the deposit logic
        BigDecimal currentBalance = savingsAccount.getBalance();
        BigDecimal updatedBalance = currentBalance.add(amountToDeposit);
        savingsAccount.setBalance(updatedBalance);
        savingsAccountRepository.save(savingsAccount);
    }
}
