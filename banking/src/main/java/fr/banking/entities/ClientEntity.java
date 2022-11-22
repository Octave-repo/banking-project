package fr.banking.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ClientEntity {
    @Id
    @GeneratedValue
    private long id;
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotBlank
    private Date dateNaissance;
    @NotBlank
    private String adresse;
    @NotBlank
    private String telephone;
    @ManyToMany
    private List<CompteEntity> comptes;
    @NotBlank
    private Date dateCreation;
    private Date dateModification;
}
