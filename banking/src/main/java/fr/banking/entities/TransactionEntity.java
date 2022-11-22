package fr.banking.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String raison;
    @OneToMany
    private CompteEntity emetteur;
    private double valeur;

    private TypeTransaction typeTransaction;
    private TypeSource typeSource;
    @NotBlank
    private Date date;
    @NotBlank
    private Date dateCreation;
}
