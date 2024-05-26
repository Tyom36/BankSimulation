package rogue.tyom.sub.securityapp.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.repositories.BankAccountRepository;
import rogue.tyom.sub.securityapp.repositories.PersonRepository;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PersonRegistrationService {

    private final PersonRepository personRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Person person) {
        BankAccount bankAccount = person.getBankAccount();
        bankAccount.setPassword(passwordEncoder.encode(bankAccount.getPassword()));

        BigDecimal depositLimit = (bankAccount.getBalance()).multiply(new BigDecimal("2.07"));
        bankAccount.setDepositLimit(depositLimit);

        System.out.println(person);
        personRepository.save(person);
    }
}
