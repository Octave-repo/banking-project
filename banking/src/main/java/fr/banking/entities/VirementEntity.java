package fr.banking.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class VirementEntity extends TransactionEntity{
    private CompteEntity receveur;
}
