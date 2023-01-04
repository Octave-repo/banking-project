package fr.banking.services.dto.virement;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostVirementRequest {
    private String ibanCompteEmetteur;
    private String ibanCompteBeneficiaire;
    private double montant;
    private String libelleVirement;
}
