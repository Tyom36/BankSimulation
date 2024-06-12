package rogue.tyom.sub.securityapp.specifications;

import org.springframework.data.jpa.domain.Specification;
import rogue.tyom.sub.securityapp.models.Person;

import java.time.LocalDate;

public class PersonSpecifications {
    public static Specification<Person> withBirthDateAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), date);
        };
    }

    public static Specification<Person> withPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) -> {
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.join("numbers").get("phoneNumber"), phoneNumber);
        };
    }

    public static Specification<Person> withFullNameStartingWith(String fullName) {
        return (root, query, criteriaBuilder) -> {
            if (fullName == null || fullName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("fullName"), fullName + "%");
        };
    }

    public static Specification<Person> withEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.join("emails").get("email"), email);
        };
    }
}
