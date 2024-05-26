package rogue.tyom.sub.securityapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rogue.tyom.sub.securityapp.models.PhoneNumber;
import rogue.tyom.sub.securityapp.security.JwtUtil;
import rogue.tyom.sub.securityapp.services.PhoneNumberService;

import java.util.List;

@RestController
@RequestMapping("/change")
@AllArgsConstructor
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;
    private final JwtUtil jwtUtil;

    @GetMapping("/{personId}/numbers")
    public ResponseEntity<?> findAllPhoneNumbersByPersonId(@PathVariable("personId") int personId,
                                                           @RequestHeader("Authorization") String authorizationHeader) {
        jwtUtil.checkForPersonalData(personId, authorizationHeader);

        List<PhoneNumber> numbers = phoneNumberService.findAllByPersonId(personId);
        return ResponseEntity.ok(numbers);
    }

    @PostMapping("/{personId}/numbers")
    public ResponseEntity<?> addNewPhoneNumber(@PathVariable("personId") int personId,
                                               @RequestHeader("Authorization") String authorizationHeader,
                                               @RequestBody @Valid PhoneNumber phoneNumber) {
        jwtUtil.checkForPersonalData(personId, authorizationHeader);

        phoneNumberService.createNewPhoneNumber(personId, phoneNumber);
        return ResponseEntity.ok("Телефон успешно добавлен");
    }

    @PatchMapping("/{personId}/numbers/{numberId}")
    public ResponseEntity<?> updatePhoneNumber(@PathVariable("personId") int personId,
                                               @PathVariable("numberId") int numberId,
                                               @RequestHeader("Authorization") String authorizationHeader,
                                               @RequestBody @Valid PhoneNumber phoneNumber) {
        jwtUtil.checkForPersonalData(personId, authorizationHeader);

        phoneNumberService.updatePhoneNumber(numberId, phoneNumber);
        return ResponseEntity.ok("Телефон успешно обновлен");
    }

    @DeleteMapping("/{personId}/numbers/{numberId}")
    public ResponseEntity<?> deletePhoneNumber(@PathVariable("personId") int personId,
                                               @PathVariable("numberId") int numberId,
                                               @RequestHeader("Authorization") String authorizationHeader) {
        jwtUtil.checkForPersonalData(personId, authorizationHeader);

        return ResponseEntity.ok(phoneNumberService.deletePhoneNumber(personId, numberId));
    }
}
