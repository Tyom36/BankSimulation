package rogue.tyom.sub.securityapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class RegistrationPersonDto {

    @Pattern(regexp = "^[А-Яа-я]+ [А-Яа-я]+ [А-Яа-я]+$",
            message = "Необходимо ввести фамилию, имя, отчество через пробел")
    @Column(name = "full_name")
    private String fullName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^\\+\\d\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}$",
            message = "Введите номер в нужном формате")
    private String phoneNumber;

    @Email
    private String email;

    @NotNull(message = "Должен быть создан банковский аккаунт")
    private BankAccountDto bankAccountDto;

}
