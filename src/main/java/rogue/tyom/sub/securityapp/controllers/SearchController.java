package rogue.tyom.sub.securityapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.services.PersonSearchService;

import java.util.List;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private final PersonSearchService personSearchService;

    @GetMapping()
    public ResponseEntity<?> getPersonAfterFilters(@RequestParam(name = "birth_date", required = false) String birthDate,
                                                   @RequestParam(name = "phone_number", required = false) String phoneNumber,
                                                   @RequestParam(name = "full_name", required = false) String fullName,
                                                   @RequestParam(name = "email", required = false) String email,
                                                   @RequestParam(name = "page", required = false) Integer page,
                                                   @RequestParam(name = "items_per_page", required = false) Integer itemsPerPage) {
        System.out.println("testing github plugin");
        List<Person> filteredPersons = personSearchService.findPersonsWithFilters(birthDate, phoneNumber, fullName, email, page, itemsPerPage);
        return ResponseEntity.ok(filteredPersons);
    }
}
