package rogue.tyom.sub.securityapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rogue.tyom.sub.securityapp.models.BankAccount;

import java.time.LocalDate;

@Getter @Setter
public class PersonDto {

    @Pattern(regexp = "^[А-Яа-я]+ [А-Яа-я]+ [А-Яа-я]+$",
            message = "Необходимо ввести фамилию, имя, отчество через пробел")
    @Column(name = "full_name")
    private String fullName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "Должен быть создан банковский аккаунт")
    private BankAccountDto bankAccountDto;

}
