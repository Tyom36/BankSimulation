package rogue.tyom.sub.securityapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rogue.tyom.sub.securityapp.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
