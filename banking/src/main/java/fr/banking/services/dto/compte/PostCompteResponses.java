package fr.banking.services.dto.compte;

import fr.banking.entities.TypeCompte;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCompteResponses {
    private String intituleCompte;
    private TypeCompte typeCompte;
    private List<GetCompteResponses.GetCompteClientResponses> titulairesCompte;
    private String iBAN;
    private double solde;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetCompteClientResponses {
        private long id;
    }
}
