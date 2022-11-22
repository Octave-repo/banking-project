package fr.banking.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class CompteEntity {
    @Id
    private String iBAN;
    private Double solde;
}
