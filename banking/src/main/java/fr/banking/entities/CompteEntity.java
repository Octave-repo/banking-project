package fr.banking.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CompteEntity {
    @Id
    private String iBAN;
    @NotBlank
    private double solde;
    private String intituleCompte;
    private TypeCompte typeCompte;

    @OneToMany
    List<ClientEntity> clients;

    @OneToMany
    List<CarteEntity> cartes;
    @NotBlank
    private Date dateCreation;
    @OneToMany
    private List<TransactionEntity> transactions;
}
