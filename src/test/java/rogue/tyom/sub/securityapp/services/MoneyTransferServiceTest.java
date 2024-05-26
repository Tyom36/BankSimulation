package rogue.tyom.sub.securityapp.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import rogue.tyom.sub.securityapp.dto.TransferDto;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.security.JwtUtil;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MoneyTransferServiceTest {

    @Mock
    private BankAccountDetailsService bankAccountDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private MoneyTransferService moneyTransferService;

    @Test
    public void testProceed_SuccessfulTransfer() {
        TransferDto transferDto = new TransferDto("fromAccount", "toAccount", new BigDecimal("100.00"));
        String authorizationHeader = "Bearer token";

        BankAccount fromAccount = new BankAccount("fromAccount", new BigDecimal("500.00"));
        BankAccount toAccount = new BankAccount("toAccount", new BigDecimal("100.00"));

        when(bankAccountDetailsService.findByUsername("fromAccount")).thenReturn(Optional.of(fromAccount));
        when(bankAccountDetailsService.findByUsername("toAccount")).thenReturn(Optional.of(toAccount));
        when(jwtUtil.getUsernameFromToken(authorizationHeader)).thenReturn("fromAccount");

        String result = moneyTransferService.proceedMoneyTransfer(transferDto, authorizationHeader);

        assertEquals("Перевод выполнен успешно!", result);
        assertEquals(new BigDecimal("400.00"), fromAccount.getBalance());
        assertEquals(new BigDecimal("200.00"), toAccount.getBalance());
    }

    @Test
    public void testProceed_InsufficientFunds() {
        TransferDto transferDto = new TransferDto("fromAccount", "toAccount", new BigDecimal("1000.00"));
        String authorizationHeader = "Bearer token";

        BankAccount fromAccount = new BankAccount("fromAccount", new BigDecimal("500.00"));
        BankAccount toAccount = new BankAccount("toAccount", new BigDecimal("100.00"));

        when(bankAccountDetailsService.findByUsername("fromAccount")).thenReturn(Optional.of(fromAccount));
        when(bankAccountDetailsService.findByUsername("toAccount")).thenReturn(Optional.of(toAccount));
        when(jwtUtil.getUsernameFromToken(authorizationHeader)).thenReturn("fromAccount");

        String result = moneyTransferService.proceedMoneyTransfer(transferDto, authorizationHeader);

        assertEquals("Недостаточно средств", result);
        assertEquals(new BigDecimal("500.00"), fromAccount.getBalance());
        assertEquals(new BigDecimal("100.00"), toAccount.getBalance());
    }

}
