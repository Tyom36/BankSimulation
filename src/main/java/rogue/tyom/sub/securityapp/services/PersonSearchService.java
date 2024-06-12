package rogue.tyom.sub.securityapp.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.repositories.PersonRepository;
import rogue.tyom.sub.securityapp.specifications.PersonSpecifications;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonSearchService {

    private final PersonRepository personRepository;

    public List<Person> findPersonsWithFilters(String birthDate, String phoneNumber, String fullName, String email, Integer page, Integer itemsPerPage) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, itemsPerPage != null ? itemsPerPage : 5);

        LocalDate date = null;
        if (birthDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date = LocalDate.parse(birthDate, formatter);
        }

        Specification<Person> spec = Specification.where(PersonSpecifications.withBirthDateAfter(date))
                .and(PersonSpecifications.withPhoneNumber(phoneNumber))
                .and(PersonSpecifications.withFullNameStartingWith(fullName))
                .and(PersonSpecifications.withEmail(email));
        return personRepository.findAll(spec, pageable).getContent();
    }
}
