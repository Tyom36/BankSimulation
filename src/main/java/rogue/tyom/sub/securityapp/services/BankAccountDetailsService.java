package rogue.tyom.sub.securityapp.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.repositories.BankAccountRepository;
import rogue.tyom.sub.securityapp.security.BankAccountDetails;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BankAccountDetailsService implements UserDetailsService {

    private final BankAccountRepository bankAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BankAccount> bankAccount = bankAccountRepository.findByUsername(username);

        if (bankAccount.isEmpty()) {
            throw new UsernameNotFoundException("Такой пользователь не найден!");
        }

        return new BankAccountDetails(bankAccount.get());
    }
}
