package rogue.tyom.sub.securityapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rogue.tyom.sub.securityapp.models.PersonEmail;
import rogue.tyom.sub.securityapp.security.JwtUtil;
import rogue.tyom.sub.securityapp.services.PersonEmailService;

import java.util.List;

@RestController
@RequestMapping("/change")
@AllArgsConstructor
public class EmailController {

    private final PersonEmailService personEmailService;
    private final JwtUtil jwtUtil;

    @GetMapping("/{id}/emails")
    public ResponseEntity<?> findAllEmailsByPersonId(@PathVariable("id") int personId,
                                                     @RequestHeader("Authorization") String authorizationHeader) {
        jwtUtil.checkForPersonalData(personId, authorizationHeader);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        List<PersonEmail> emails = personEmailService.findAllByPersonId(personId);
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/{id}/emails")
    public ResponseEntity<?> addNewEmail(@PathVariable("id") int personId,
                                         @RequestHeader("Authorization") String authorizationHeader,
                                         @RequestBody @Valid PersonEmail personEmail) {
        jwtUtil.checkForPersonalData(personId, authorizationHeader);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        personEmailService.createNewEmail(personId, personEmail);
        return ResponseEntity.ok("Почта успешно добавлена");
    }

    @PatchMapping("/{personId}/emails/{emailId}")
    public ResponseEntity<?> updateEmail(@PathVariable("personId") int personId,
                                         @PathVariable("emailId") int emailId,
                                         @RequestHeader("Authorization") String authorizationHeader,
                                         @RequestBody @Valid PersonEmail personEmail) {
        jwtUtil.checkForPersonalData(personId, authorizationHeader);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        personEmailService.updateEmail(emailId, personEmail);
        return ResponseEntity.ok("Почта успешно обновлена");
    }

    @DeleteMapping("/{personId}/emails/{emailId}")
    public ResponseEntity<?> deleteEmail(@PathVariable("personId") int personId,
                                         @PathVariable("emailId") int emailId,
                                         @RequestHeader("Authorization") String authorizationHeader) {
        jwtUtil.checkForPersonalData(personId, authorizationHeader);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        return ResponseEntity.ok(personEmailService.deleteEmail(personId, emailId));
    }
}
