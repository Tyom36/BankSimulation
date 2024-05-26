package rogue.tyom.sub.securityapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rogue.tyom.sub.securityapp.models.Person;

import java.time.LocalDate;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Page<Person> findByDateOfBirthAfter(LocalDate birthDate, Pageable pageable);
    Page<Person> findByFullNameStartingWith(String fullName, Pageable pageable);

}
