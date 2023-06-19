package com.acme.test01.tornikeshelia.persistence.repository.currentacc;

import com.acme.test01.tornikeshelia.model.CurrentAccount;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This is a dummy DB access repository class .
 * in real life this would be an interface that extends JPA repository
 **/

@Repository
public class CurrentAccountRepository {

    public Long openCurrentAccount(CurrentAccount currentAccount) {

        System.out.println("current account opened successfully");
        return currentAccount.getAccountId();
    }

    public CurrentAccount getAccount(Long customerNum) {
        /** This is only for testing purposes, if customerNum == 0, I assume that we are opening
         * a new account. Again, only for testing purposes as we don't have a real database **/

        if (customerNum == 0L) {
            return null;
        }
        return new CurrentAccount(1L,
                customerNum,
                BigDecimal.ZERO,
                BigDecimal.valueOf(25000),
                new Date(), new Date());
    }

    public void save(CurrentAccount currentAccount) {
        System.out.println("Successfully updated current account balance");
    }
}
