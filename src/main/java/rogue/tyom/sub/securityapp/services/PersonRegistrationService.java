package rogue.tyom.sub.securityapp.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import rogue.tyom.sub.securityapp.dto.AuthenticationDto;
import rogue.tyom.sub.securityapp.dto.RegistrationPersonDto;
import rogue.tyom.sub.securityapp.mappers.PersonMapper;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.models.PersonEmail;
import rogue.tyom.sub.securityapp.models.PhoneNumber;
import rogue.tyom.sub.securityapp.repositories.PersonRepository;
import rogue.tyom.sub.securityapp.security.JwtUtil;
import rogue.tyom.sub.securityapp.util.PersonValidator;

import java.math.BigDecimal;
import java.util.Collections;

@Service
@AllArgsConstructor
public class PersonRegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PersonMapper personMapper;
    private final PersonValidator personValidator;

    @Transactional
    public Person registerNewPerson(RegistrationPersonDto registrationPersonDto, BindingResult bindingResult) {
        Person person = createAndValidatePerson(registrationPersonDto, bindingResult);
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

        return person;
    }

    public Person createAndValidatePerson(RegistrationPersonDto registrationPersonDto, BindingResult bindingResult) {
        PersonEmail email = new PersonEmail(registrationPersonDto.getEmail());
        PhoneNumber phoneNumber = new PhoneNumber(registrationPersonDto.getPhoneNumber());
        Person person = personMapper.mapPerson(registrationPersonDto);
        person.setEmails(Collections.singletonList(email));
        person.setNumbers(Collections.singletonList(phoneNumber));

        personValidator.validate(person, bindingResult);

        return person;
    }

    public boolean isValidCredentials(AuthenticationDto authenticationDto, BindingResult bindingResult) {
        BankAccount bankAccount = personMapper.mapAuthentication(authenticationDto);
        personValidator.validate(bankAccount, bindingResult);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(),
                        authenticationDto.getPassword());

        try {
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            return false;
        }
        return true;
    }

}
