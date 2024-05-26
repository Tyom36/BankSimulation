package rogue.tyom.sub.securityapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rogue.tyom.sub.securityapp.models.Person;
import rogue.tyom.sub.securityapp.models.PhoneNumber;
import rogue.tyom.sub.securityapp.repositories.PhoneNumberRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final PersonService personService;


    public List<PhoneNumber> findAllByPersonId(int personId) {
        return phoneNumberRepository.findAllByPersonId(personId);
    }

    @Transactional
    public void createNewPhoneNumber(int personId, PhoneNumber phoneNumber) {
        Optional<Person> person = personService.findById(personId);
        person.ifPresent(phoneNumber::setPerson);
        phoneNumberRepository.save(phoneNumber);
    }

    @Transactional
    public void updatePhoneNumber(int numberId, PhoneNumber phoneNumber) {
        Optional<PhoneNumber> number = phoneNumberRepository.findById(numberId);
        number.ifPresent(value -> value.setPhoneNumber(phoneNumber.getPhoneNumber()));
    }

    @Transactional
    public String deletePhoneNumber(int personId, int numberId) {
        if (phoneNumberRepository.countByPersonId(personId) <= 1) {
            return "Невозможно удалить последний номер телефона";
        } else {
            phoneNumberRepository.deleteById(numberId);
            return "Телефон успешно удален";
        }
    }
}
