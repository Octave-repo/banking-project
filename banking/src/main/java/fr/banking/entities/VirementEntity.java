package fr.banking.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
//@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VirementEntity{
    private CompteEntity receveur;
}
