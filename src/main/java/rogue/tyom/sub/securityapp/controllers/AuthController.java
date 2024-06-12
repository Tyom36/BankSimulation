package rogue.tyom.sub.securityapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rogue.tyom.sub.securityapp.dto.AuthenticationDto;
import rogue.tyom.sub.securityapp.dto.RegistrationPersonDto;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.security.JwtUtil;
import rogue.tyom.sub.securityapp.services.PersonRegistrationService;

@RestController
@AllArgsConstructor
public class AuthController {

    private final PersonRegistrationService personRegistrationService;
    private final JwtUtil jwtUtil;

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid RegistrationPersonDto registrationPersonDto,
                                                   BindingResult bindingResult) {
        Person person = personRegistrationService.registerNewPerson(registrationPersonDto, bindingResult);

        HttpHeaders headers = new HttpHeaders();
        String token = jwtUtil.generateToken(person.getBankAccount().getUsername());
        headers.add("jwt-token", token);
        System.out.println("test");

        return new ResponseEntity<>("Регистрация прошла успешно!", headers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody @Valid AuthenticationDto authenticationDto,
                                          BindingResult bindingResult) {
        HttpHeaders headers = new HttpHeaders();
        if (!personRegistrationService.isValidCredentials(authenticationDto, bindingResult)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный пароль!");
        }

        String token = jwtUtil.generateToken(authenticationDto.getUsername());
        headers.add("jwt-token", token);

        return new ResponseEntity<>("Вы зашли в систему",headers, HttpStatus.OK);
    }

}
