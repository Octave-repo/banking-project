package fr.banking.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
public class CompteEntity {
    private String iBAN;
    private Double solde;
}
