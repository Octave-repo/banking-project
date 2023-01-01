package fr.banking.entities;

import lombok.*;

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
    @GeneratedValue
    private Long cardNumber;

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
