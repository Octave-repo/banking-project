package fr.banking.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
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
    private double solde;
    private String intituleCompte;
    private TypeCompte typeCompte;

    @OneToMany
    @JoinColumn
    List<ClientEntity> clients;

    @OneToMany
    @JoinColumn
    List<CarteEntity> cartes;
    private Timestamp dateCreation;
    @OneToMany
    @JoinColumn
    private List<TransactionEntity> transactions;
}
