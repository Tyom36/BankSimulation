package rogue.tyom.sub.securityapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import rogue.tyom.sub.securityapp.dto.AuthenticationDto;
import rogue.tyom.sub.securityapp.dto.PersonDto;
import rogue.tyom.sub.securityapp.mappers.PersonDtoToPersonMapper;
import rogue.tyom.sub.securityapp.mappers.PersonMapper;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.security.JwtUtil;
import rogue.tyom.sub.securityapp.services.PersonRegistrationService;
import rogue.tyom.sub.securityapp.util.PersonLoginException;
import rogue.tyom.sub.securityapp.util.PersonValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthController {

    private final PersonRegistrationService personRegistrationService;
    private final PersonValidator personValidator;
    private final PersonMapper personMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PersonDtoToPersonMapper personDtoToPersonMapper;

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid PersonDto personDto,
                                                   BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        Person person = personDtoToPersonMapper.map(personDto);

        //Person person = personMapper.convertToPerson(personDto);



        personRegistrationService.register(person);

        String token = jwtUtil.generateToken(person.getBankAccount().getUsername());
        response.put("jwt-token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody @Valid AuthenticationDto authenticationDto,
                                          BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();

            StringBuilder builder = new StringBuilder();
            for (FieldError error : errors) {
                builder.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append("; ");
            }
            throw new PersonLoginException(builder.toString().trim());

        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(),
                        authenticationDto.getPassword());

        try {
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            response.put("Сообщение", "Неверный пароль!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = jwtUtil.generateToken(authenticationDto.getUsername());
        response.put("jwt-token", token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestHeader("Authorization") String authorizationHeader) {
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        String username = jwtUtil.validateTokenAndGetUsername(token);
        return "hello, " + username;
    }

}
