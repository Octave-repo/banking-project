package fr.banking.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CompteEntity {
    @Id
    private String iBAN;
    @NotBlank
    private double solde;
    private String intituleCompte;
    private TypeCompte typeCompte;

    @ManyToMany
    List<ClientEntity> clients;

    @OneToMany
    List<CarteEntity> cartes;
    @NotBlank
    private Date dateCreation;
}
