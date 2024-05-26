package rogue.tyom.sub.securityapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.repositories.PersonRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }


}
