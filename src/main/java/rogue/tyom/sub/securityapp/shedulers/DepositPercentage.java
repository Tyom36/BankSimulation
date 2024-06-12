package rogue.tyom.sub.securityapp.shedulers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.services.BankAccountDetailsService;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class DepositPercentage {

    private final BankAccountDetailsService bankAccountDetailsService;

    //@Scheduled(cron = "0 * * * * *")
    @Scheduled(fixedRate = 60_000)
    @Transactional
    public void increaseDeposits() {
        List<BankAccount> accounts = bankAccountDetailsService.findAllAccounts();

        for (BankAccount account : accounts) {
            BigDecimal result = (account.getBalance()).multiply(new BigDecimal(1.05));
            BigDecimal limit = account.getDepositLimit();

            if (result.compareTo(limit) < 0) {
                account.setBalance(result);
            }
        }
    }
}
