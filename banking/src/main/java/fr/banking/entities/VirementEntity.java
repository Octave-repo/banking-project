package fr.banking.entities;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
//@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VirementEntity{
    private CompteEntity receveur;
}
