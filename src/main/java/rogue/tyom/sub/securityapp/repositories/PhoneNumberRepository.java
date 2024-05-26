package rogue.tyom.sub.securityapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rogue.tyom.sub.securityapp.models.PhoneNumber;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {

    List<PhoneNumber> findAllByPersonId(int personId);

    Integer countByPersonId(int personId);

    Page<PhoneNumber> findByPhoneNumber(String phoneNumber, Pageable pageable);
}
