package fr.banking.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue
    private long id;
    private String nom;
    private String prenom;
    private int age;
    private String adresse;
    private String telephone;
}
