package fr.banking.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VirementEntity{
    @Id
    @GeneratedValue
    private long id;

    private Timestamp dateCreation;
    //Un virement est composé de 2 transactions
    @OneToOne
    private TransactionEntity transactionEmmeteur;
    @OneToOne
    private TransactionEntity transactionRecepteur;
}
