package com.acme.test01.tornikeshelia.persistence.repository.savingacc;

import com.acme.test01.tornikeshelia.model.SavingsAccount;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/** This is a dummy DB access repository class .
 * in real life this would be an interface that extends JPA repository
 * **/

@Repository
public class SavingsAccountRepository {

    public Long openSavingsAccount(SavingsAccount savingsAccount) {

        System.out.println("savings account opened successfully");
        return savingsAccount.getAccountId();
    }

    public SavingsAccount getAccount(Long customerNum) {
        /** This is only for testing purposes, if customerNum == 0, I assume that we are opening
         * a new account. Again, only for testing purposes as we don't have a real database **/

        if (customerNum == 0L) {
            return null;
        }
        return new SavingsAccount(1L, customerNum, BigDecimal.valueOf(1000), new Date(), new Date());
    }

    public void save(SavingsAccount savingsAccount) {
        System.out.println("Successfully updated savings account");
    }
}
