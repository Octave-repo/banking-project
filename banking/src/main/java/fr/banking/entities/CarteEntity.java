package fr.banking.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CarteEntity {
    @Id
    private String cardNumber;

    @ManyToOne
    private CompteEntity compte;
    @NotBlank
    private String password;
    @NotBlank
    private String moisExpiration;
    @NotBlank
    private String anneeExpiration;

}