package com.acme.test01.tornikeshelia.account.savingsaccount;

import com.acme.test01.tornikeshelia.exception.model.GeneralException;
import com.acme.test01.tornikeshelia.exception.model.WithdrawalAmountTooLargeException;
import com.acme.test01.tornikeshelia.model.SavingsAccount;
import com.acme.test01.tornikeshelia.persistence.repository.savingacc.SavingsAccountRepository;
import com.acme.test01.tornikeshelia.service.impl.SavingsAccountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SavingsAccountImplTest {
    @Mock
    private SavingsAccountRepository savingsAccountRepository;

    private SavingsAccountImpl savingsAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        savingsAccount = new SavingsAccountImpl(savingsAccountRepository);
    }

    @Test
    void testWithdraw_ValidAmount_Success() {
        // Prepare test data
        Long accountId = 1L;
        BigDecimal currentBalance = BigDecimal.valueOf(2000L);
        BigDecimal amountToWithdraw = BigDecimal.valueOf(1000L);

        SavingsAccount account = new SavingsAccount(accountId,
                1L,
                currentBalance,
                new Date(),
                new Date());
        when(savingsAccountRepository.getAccount(accountId)).thenReturn(account);

        // Perform the withdrawal
        assertDoesNotThrow(() -> savingsAccount.withdraw(accountId, amountToWithdraw));

        // Verify the updated balance and save method invocation
        BigDecimal expectedBalance = BigDecimal.valueOf(1000L);
        assertEquals(expectedBalance, account.getBalance());
        verify(savingsAccountRepository, times(1)).save(account);
    }

    @Test
    void testWithdraw_InsufficientBalance_ThrowsWithdrawalAmountTooLargeException() {
        // Prepare test data
        Long accountId = 1L;
        BigDecimal currentBalance = BigDecimal.valueOf(500L);
        BigDecimal amountToWithdraw = BigDecimal.valueOf(1000L);


        SavingsAccount account = new SavingsAccount(accountId,
                1L,
                currentBalance,
                new Date(),
                new Date());
        when(savingsAccountRepository.getAccount(accountId)).thenReturn(account);

        // Perform the withdrawal and assert exception
        assertThrows(WithdrawalAmountTooLargeException.class, () ->
                savingsAccount.withdraw(accountId, amountToWithdraw));

        // Verify the balance and save method invocation
        assertEquals(currentBalance, account.getBalance());
        verify(savingsAccountRepository, never()).save(account);
    }

    @Test
    void testDeposit_ValidAmount_Success() {
        // Prepare test data
        Long accountId = 1L;
        BigDecimal currentBalance = BigDecimal.valueOf(2000L);
        BigDecimal amountToDeposit = BigDecimal.valueOf(500L);

        SavingsAccount account = new SavingsAccount(accountId,
                1L,
                currentBalance,
                new Date(),
                new Date());
        when(savingsAccountRepository.getAccount(accountId)).thenReturn(account);

        // Perform the deposit
        assertDoesNotThrow(() -> savingsAccount.deposit(accountId, amountToDeposit));

        // Verify the updated balance and save method invocation
        BigDecimal expectedBalance = BigDecimal.valueOf(2500L);
        assertEquals(expectedBalance, account.getBalance());
        verify(savingsAccountRepository, times(1)).save(account);
    }

    @Test
    void testDeposit_InvalidAmount_ThrowsGeneralException() {
        // Prepare test data
        Long accountId = 1L;
        BigDecimal currentBalance = BigDecimal.valueOf(2000L);
        BigDecimal amountToDeposit = BigDecimal.ZERO;

        SavingsAccount account = new SavingsAccount(accountId,
                1L,
                currentBalance,
                new Date(),
                new Date());
        when(savingsAccountRepository.getAccount(accountId)).thenReturn(account);

        // Perform the deposit and assert exception
        assertThrows(GeneralException.class, () ->
                savingsAccount.deposit(accountId, amountToDeposit));

        // Verify the balance and save method invocation
        assertEquals(currentBalance, account.getBalance());
        verify(savingsAccountRepository, never()).save(account);
    }
}