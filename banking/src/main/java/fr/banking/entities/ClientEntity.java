package fr.banking.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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
