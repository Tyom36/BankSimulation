package rogue.tyom.sub.securityapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "email")
@Getter @Setter
@NoArgsConstructor
public class PersonEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private Person person;

    public PersonEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PersonEmail{" +
               "email='" + email + '\'' +
               '}';
    }
}
