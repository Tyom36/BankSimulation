package rogue.tyom.sub.securityapp.services;

import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rogue.tyom.sub.securityapp.dto.TransferDto;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.security.JwtUtil;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MoneyTransferService {

    private final BankAccountDetailsService bankAccountDetailsService;
    private final JwtUtil jwtUtil;

    @Transactional
    public String proceedMoneyTransfer(TransferDto transferDto, String authorizationHeader) {
        try {
            Optional<BankAccount> bankAccountFrom = bankAccountDetailsService.findByUsername(transferDto.getFromAccount());
            Optional<BankAccount> bankAccountTo = bankAccountDetailsService.findByUsername(transferDto.getToAccount());
            BigDecimal value = transferDto.getValue();
            String username = jwtUtil.getUsernameFromToken(authorizationHeader);

            if (bankAccountFrom.isPresent() && bankAccountTo.isPresent() && bankAccountFrom.get().getUsername().equals(username)) {
                BigDecimal balanceFrom = bankAccountFrom.get().getBalance();
                BigDecimal balanceTo = bankAccountTo.get().getBalance();

                if (balanceFrom.compareTo(value) > 0) {
                    bankAccountFrom.get().setBalance(balanceFrom.subtract(value));
                    bankAccountTo.get().setBalance(balanceTo.add(value));
                    return "Перевод выполнен успешно!";
                }
                return "Недостаточно средств";
            }
            return "Неверные данные для перевода";
        } catch (OptimisticLockException ex) {
            return "Конфликт версий при обновлении банковского счета.";
        }
    }
}
