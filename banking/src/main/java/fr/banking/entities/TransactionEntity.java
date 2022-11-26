package fr.banking.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String raison;
    @ManyToOne
    private CompteEntity emetteur;
    private double valeur;

    private TypeTransaction typeTransaction;
    private TypeSource typeSource;
    @NotBlank
    private Timestamp date;
    @NotBlank
    private Timestamp dateCreation;
}
