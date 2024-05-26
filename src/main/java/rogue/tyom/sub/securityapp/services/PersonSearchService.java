package rogue.tyom.sub.securityapp.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.models.PersonEmail;
import rogue.tyom.sub.securityapp.models.PhoneNumber;
import rogue.tyom.sub.securityapp.repositories.PersonEmailRepository;
import rogue.tyom.sub.securityapp.repositories.PersonRepository;
import rogue.tyom.sub.securityapp.repositories.PhoneNumberRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PersonSearchService {

    private final PersonRepository personRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final PersonEmailRepository personEmailRepository;

    public List<Person> findPersonsByFilters(String birthDate, String phoneNumber, String fullName, String email, Integer page, Integer itemsPerPage) {
        Pageable pageable = null;
        if (page != null && itemsPerPage != null) {
            pageable = PageRequest.of(page, itemsPerPage);
        } else {
            pageable = PageRequest.of(0, 10);
        }

        List<Person> result = new ArrayList<>();

        if (birthDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(birthDate, formatter);
            result.addAll(personRepository.findByDateOfBirthAfter(date, pageable).getContent());
        }

        if (phoneNumber != null) {
            List<PhoneNumber> numbers = phoneNumberRepository.findByPhoneNumber(phoneNumber, pageable).getContent();
            Set<Person> persons = new HashSet<>();
            for(PhoneNumber number : numbers) {
                persons.add(number.getPerson());
            }
            List<Person> filteredByPhoneNumber = new ArrayList<>(persons);
            if (!result.isEmpty()) {
                result.retainAll(filteredByPhoneNumber);
            } else {
                result.addAll(filteredByPhoneNumber);
            }
        }

        if (fullName != null) {
            List<Person> filteredByFullName = personRepository.findByFullNameStartingWith(fullName, pageable).getContent();
            if (!result.isEmpty()) {
                result.retainAll(filteredByFullName);
            } else {
                result.addAll(filteredByFullName);
            }
        }

        if (email != null) {
            List<PersonEmail> emails = personEmailRepository.findByEmail(email, pageable).getContent();
            Set<Person> persons = new HashSet<>();
            for(PersonEmail mail : emails) {
                persons.add(mail.getPerson());
            }
            List<Person> filteredByEmail = new ArrayList<>(persons);
            if (!result.isEmpty()) {
                result.retainAll(filteredByEmail);
            } else {
                result.addAll(filteredByEmail);
            }
        }

        return result;
    }
}
