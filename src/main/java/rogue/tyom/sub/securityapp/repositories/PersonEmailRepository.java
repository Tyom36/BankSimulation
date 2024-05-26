package rogue.tyom.sub.securityapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rogue.tyom.sub.securityapp.models.PersonEmail;

import java.util.List;

@Repository
public interface PersonEmailRepository extends JpaRepository<PersonEmail, Integer> {

    List<PersonEmail> findAllByPersonId(int personId);
    Integer countByPersonId(int personId);

    Page<PersonEmail> findByEmail(String email, Pageable pageable);

}
