package com.acme.test01.tornikeshelia.account;

import com.acme.test01.tornikeshelia.exception.model.GeneralException;
import com.acme.test01.tornikeshelia.model.CurrentAccount;
import com.acme.test01.tornikeshelia.model.SavingsAccount;
import com.acme.test01.tornikeshelia.persistence.repository.currentacc.CurrentAccountRepository;
import com.acme.test01.tornikeshelia.persistence.repository.savingacc.SavingsAccountRepository;
import com.acme.test01.tornikeshelia.service.AccountAbstract;
import com.acme.test01.tornikeshelia.service.impl.CurrentAccountImpl;
import com.acme.test01.tornikeshelia.service.impl.SavingsAccountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountAbstractTest {

    private SavingsAccountRepository savingsAccountRepository;
    private CurrentAccountRepository currentAccountRepository;
    private AccountAbstract currentAccountAbstract;
    private AccountAbstract savingsAccountAbstract;

    @BeforeEach
    void setUp() {
        savingsAccountRepository = mock(SavingsAccountRepository.class);
        currentAccountRepository = mock(CurrentAccountRepository.class);
        currentAccountAbstract = new CurrentAccountImpl(currentAccountRepository);
        savingsAccountAbstract = new SavingsAccountImpl(savingsAccountRepository);
    }

    @Test
    void openSavingsAccount_validAmountToDeposit_accountOpened() {
        Long customerNum = 123L;
        BigDecimal amountToDeposit = BigDecimal.valueOf(2000L);

        when(savingsAccountRepository.getAccount(0L)).thenReturn(null);

        assertDoesNotThrow(() -> savingsAccountAbstract.openSavingsAccount(customerNum, amountToDeposit));

        verify(savingsAccountRepository).openSavingsAccount(any(SavingsAccount.class));
    }

    @Test
    void openSavingsAccount_existingSavingsAccount_throwsException() {
        Long customerNum = 123L;
        BigDecimal amountToDeposit = BigDecimal.valueOf(2000L);

        when(savingsAccountRepository.getAccount(0L)).thenReturn(new SavingsAccount());

        assertThrows(GeneralException.class,
                () -> savingsAccountAbstract.openSavingsAccount(customerNum, amountToDeposit));

        verify(savingsAccountRepository, never()).openSavingsAccount(any(SavingsAccount.class));
    }

    @Test
    void openSavingsAccount_invalidAmountToDeposit_throwsException() {
        Long customerNum = 123L;
        BigDecimal amountToDeposit = BigDecimal.valueOf(500L);

        assertThrows(GeneralException.class,
                () -> savingsAccountAbstract.openSavingsAccount(customerNum, amountToDeposit));

        verify(savingsAccountRepository, never()).openSavingsAccount(any(SavingsAccount.class));
    }

    @Test
    void openCurrentAccount_newCurrentAccount_accountOpened() {
        Long customerNum = 123L;

        when(currentAccountRepository.getAccount(customerNum)).thenReturn(null);

        assertDoesNotThrow(() -> currentAccountAbstract.openCurrentAccount(customerNum));

        verify(currentAccountRepository).openCurrentAccount(any(CurrentAccount.class));
    }

    @Test
    void openCurrentAccount_existingCurrentAccount_throwsException() {
        Long customerNum = 123L;

        when(currentAccountRepository.getAccount(customerNum)).thenReturn(new CurrentAccount());

        assertThrows(GeneralException.class, () -> currentAccountAbstract.openCurrentAccount(customerNum));

        verify(currentAccountRepository, never()).openCurrentAccount(any(CurrentAccount.class));
    }
}