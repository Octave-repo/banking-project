package fr.banking.entities;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

    public ClientEntity (String nom,String prenom,Date date,String tel,String adresse ){
        this.nom=nom;
        this.prenom=prenom;
        dateNaissance=date;
        telephone=tel;
        this.adresse=adresse;
        dateCreation=new Date(System.currentTimeMillis());
    }


}
