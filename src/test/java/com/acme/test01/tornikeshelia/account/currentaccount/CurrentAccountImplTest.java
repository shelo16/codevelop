package com.acme.test01.tornikeshelia.account.currentaccount;

import com.acme.test01.tornikeshelia.exception.model.GeneralException;
import com.acme.test01.tornikeshelia.exception.model.WithdrawalAmountTooLargeException;
import com.acme.test01.tornikeshelia.model.CurrentAccount;
import com.acme.test01.tornikeshelia.persistence.repository.currentacc.CurrentAccountRepository;
import com.acme.test01.tornikeshelia.service.impl.CurrentAccountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CurrentAccountImplTest {
    @Mock
    private CurrentAccountRepository currentAccountRepository;

    private CurrentAccountImpl currentAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currentAccount = new CurrentAccountImpl(currentAccountRepository);
    }

    @Test
    void testWithdraw_ValidAmount_Success() {
        // Prepare test data
        Long accountId = 1L;
        BigDecimal currentBalance = BigDecimal.valueOf(2000L);
        BigDecimal overdraftLimit = BigDecimal.valueOf(1000L);
        BigDecimal amountToWithdraw = BigDecimal.valueOf(1500L);

        CurrentAccount account = new CurrentAccount(accountId,
                1L,
                currentBalance,
                overdraftLimit,
                new Date(), new Date());
        when(currentAccountRepository.getAccount(accountId)).thenReturn(account);

        // Perform the withdrawal
        assertDoesNotThrow(() -> currentAccount.withdraw(accountId, amountToWithdraw));

        // Verify the updated balance and save method invocation
        BigDecimal expectedBalance = BigDecimal.valueOf(500L);
        assertEquals(expectedBalance, account.getBalance());
        verify(currentAccountRepository, times(1)).save(account);
    }

    @Test
    void testWithdraw_AmountExceedsOverdraftLimit_ThrowsWithdrawalAmountTooLargeException() {
        // Prepare test data
        Long accountId = 1L;
        BigDecimal currentBalance = BigDecimal.valueOf(2000L);
        BigDecimal overdraftLimit = BigDecimal.valueOf(1000L);
        BigDecimal amountToWithdraw = BigDecimal.valueOf(3500L);

        CurrentAccount account = new CurrentAccount(accountId,
                1L,
                currentBalance,
                overdraftLimit,
                new Date(), new Date());
        when(currentAccountRepository.getAccount(accountId)).thenReturn(account);

        // Perform the withdrawal and assert exception
        assertThrows(WithdrawalAmountTooLargeException.class, () ->
                currentAccount.withdraw(accountId, amountToWithdraw));

        // Verify the balance and save method invocation
        assertEquals(currentBalance, account.getBalance());
        verify(currentAccountRepository, never()).save(account);
    }

    @Test
    void testDeposit_ValidAmount_Success() {
        // Prepare test data
        Long accountId = 1L;
        BigDecimal currentBalance = BigDecimal.valueOf(2000L);
        BigDecimal overdraftLimit = BigDecimal.valueOf(1000L);
        BigDecimal amountToDeposit = BigDecimal.valueOf(500L);

        CurrentAccount account = new CurrentAccount(accountId,
                1L,
                currentBalance,
                overdraftLimit,
                new Date(), new Date());
        when(currentAccountRepository.getAccount(accountId)).thenReturn(account);

        // Perform the deposit
        assertDoesNotThrow(() -> currentAccount.deposit(accountId, amountToDeposit));

        // Verify the updated balance and save method invocation
        BigDecimal expectedBalance = BigDecimal.valueOf(2500L);
        assertEquals(expectedBalance, account.getBalance());
        verify(currentAccountRepository, times(1)).save(account);
    }

    @Test
    void testDeposit_InvalidAmount_ThrowsGeneralException() {
        // Prepare test data
        Long accountId = 1L;
        BigDecimal currentBalance = BigDecimal.valueOf(2000L);
        BigDecimal overdraftLimit = BigDecimal.valueOf(1000L);
        BigDecimal amountToDeposit = BigDecimal.ZERO;

        CurrentAccount account = new CurrentAccount(accountId,
                1L,
                currentBalance,
                overdraftLimit,
                new Date(), new Date());
        when(currentAccountRepository.getAccount(accountId)).thenReturn(account);

        // Perform the deposit and assert exception
        assertThrows(GeneralException.class, () ->
                currentAccount.deposit(accountId, amountToDeposit));

        // Verify the balance and save method invocation
        assertEquals(currentBalance, account.getBalance());
        verify(currentAccountRepository, never()).save(account);
    }
}