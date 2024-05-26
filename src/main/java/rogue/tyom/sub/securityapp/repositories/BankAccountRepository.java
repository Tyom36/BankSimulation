package rogue.tyom.sub.securityapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rogue.tyom.sub.securityapp.models.BankAccount;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    Optional<BankAccount> findByUsername(String username);

}
