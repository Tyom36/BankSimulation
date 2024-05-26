package rogue.tyom.sub.securityapp.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.models.PersonEmail;
import rogue.tyom.sub.securityapp.models.PhoneNumber;
import rogue.tyom.sub.securityapp.repositories.PersonRepository;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PersonRegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerNewPerson(Person person) {
        BankAccount bankAccount = person.getBankAccount();
        bankAccount.setPassword(passwordEncoder.encode(bankAccount.getPassword()));
        bankAccount.setPerson(person);

        BigDecimal depositLimit = (bankAccount.getBalance()).multiply(new BigDecimal("3.07"));
        bankAccount.setDepositLimit(depositLimit);

        PersonEmail personEmail = person.getEmails().get(0);
        personEmail.setPerson(person);
        PhoneNumber phoneNumber = person.getNumbers().get(0);
        phoneNumber.setPerson(person);

        personRepository.save(person);
    }
}
