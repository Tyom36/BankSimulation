package rogue.tyom.sub.securityapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.models.PersonEmail;
import rogue.tyom.sub.securityapp.repositories.PersonEmailRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonEmailService {

    private final PersonEmailRepository personEmailRepository;
    private final PersonService personService;

    public List<PersonEmail> findAllByPersonId(int personId) {
        return personEmailRepository.findAllByPersonId(personId);
    }

    @Transactional
    public void createNewEmail(int personId, PersonEmail personEmail) {
        Optional<Person> person = personService.findById(personId);
        person.ifPresent(personEmail::setPerson);
        personEmailRepository.save(personEmail);
    }

    @Transactional
    public void updateEmail(int emailId, PersonEmail personEmail) {
        Optional<PersonEmail> email = personEmailRepository.findById(emailId);
        email.ifPresent(value -> value.setEmail(personEmail.getEmail()));
    }

    @Transactional
    public String deleteEmail(int personId, int emailId) {
        int size = personEmailRepository.countByPersonId(personId);
        if (size <= 1) {
            return "Невозможно удалить последнюю почту";
        } else {
            personEmailRepository.deleteById(emailId);
            return "Почта успешно удалена";
        }
    }
}
