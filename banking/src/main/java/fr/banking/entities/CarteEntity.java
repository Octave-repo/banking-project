package fr.banking.entities;

import fr.banking.generator.CardNumberGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CarteEntity {
    @Id
    @GeneratedValue(generator = CardNumberGenerator.NAME)
    @GenericGenerator(name = CardNumberGenerator.NAME, strategy = "fr.banking.generator.CardNumberGenerator")
    private String cardNumber;

    @ManyToOne
    private CompteEntity compte;
    private int password;
    @NotBlank
    private String moisExpiration;
    @NotBlank
    private String anneeExpiration;

    public String getExpirationDate(){
        return moisExpiration + "/" + anneeExpiration;
    }
}
