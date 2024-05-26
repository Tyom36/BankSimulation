package rogue.tyom.sub.securityapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rogue.tyom.sub.securityapp.dto.AuthenticationDto;
import rogue.tyom.sub.securityapp.dto.RegistrationPersonDto;
import rogue.tyom.sub.securityapp.mappers.PersonMapper;
import rogue.tyom.sub.securityapp.models.BankAccount;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.models.PersonEmail;
import rogue.tyom.sub.securityapp.models.PhoneNumber;
import rogue.tyom.sub.securityapp.security.JwtUtil;
import rogue.tyom.sub.securityapp.services.PersonRegistrationService;
import rogue.tyom.sub.securityapp.util.PersonValidator;

import java.util.Collections;

@RestController
@AllArgsConstructor
public class AuthController {

    private final PersonRegistrationService personRegistrationService;
    private final PersonValidator personValidator;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PersonMapper personMapper;

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid RegistrationPersonDto registrationPersonDto,
                                                   BindingResult bindingResult) {
        PersonEmail email = new PersonEmail(registrationPersonDto.getEmail());
        PhoneNumber phoneNumber = new PhoneNumber(registrationPersonDto.getPhoneNumber());
        Person person = personMapper.mapPerson(registrationPersonDto);
        person.setEmails(Collections.singletonList(email));
        person.setNumbers(Collections.singletonList(phoneNumber));

        personValidator.validate(person, bindingResult);

        personRegistrationService.registerNewPerson(person);

        HttpHeaders headers = new HttpHeaders();
        String token = jwtUtil.generateToken(person.getBankAccount().getUsername());
        headers.add("jwt-token", token);

        return new ResponseEntity<>("Регистрация прошла успешно!", headers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody @Valid AuthenticationDto authenticationDto,
                                          BindingResult bindingResult) {
        HttpHeaders headers = new HttpHeaders();
        BankAccount bankAccount = personMapper.mapAuthentication(authenticationDto);
        personValidator.validate(bankAccount, bindingResult);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(),
                        authenticationDto.getPassword());

        try {
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный пароль!");
        }

        String token = jwtUtil.generateToken(authenticationDto.getUsername());
        headers.add("jwt-token", token);

        return new ResponseEntity<>("Вы зашли в систему",headers, HttpStatus.OK);
    }

}
