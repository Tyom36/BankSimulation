package rogue.tyom.sub.securityapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "person")
@Getter @Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = "^[А-Яа-я]+ [А-Яа-я]+ [А-Яа-я]+$",
            message = "Необходимо ввести фамилию, имя, отчество через пробел")
    @Column(name = "full_name")
    private String fullName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "Должен быть создан банковский аккаунт")
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private BankAccount bankAccount;

    @Override
    public String toString() {
        return "Person{" +
               "fullName='" + fullName + '\'' +
               ", dateOfBirth=" + dateOfBirth +
               '}';
    }
}
