package fr.banking.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private String cardNumber;

    @ManyToOne
    private CompteEntity compte;
    @NotBlank
    private String password;
    @NotBlank
    private String moisExpiration;
    @NotBlank
    private String anneeExpiration;

    public String getExpirationDate(){
        return moisExpiration + "/" + anneeExpiration;
    }
}
