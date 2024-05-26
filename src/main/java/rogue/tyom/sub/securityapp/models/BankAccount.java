package rogue.tyom.sub.securityapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Getter @Setter
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя пользователя не может быть пустым")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

    @Min(value = 0, message = "Сумма не может быть отрицательной")
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private Person person;

    @Column(name = "deposit_limit")
    private BigDecimal depositLimit;

    @Version
    private Long version;


    public BankAccount(String username, BigDecimal balance) {
        this.username = username;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
               "username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", balance=" + balance +
               '}';
    }
}
