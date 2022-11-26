package fr.banking.services.dto.compte;

import fr.banking.entities.TypeCompte;
import fr.banking.entities.TypeSource;
import fr.banking.entities.TypeTransaction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCompteResponses {
    private String Iban;
    private double solde;
    private String intituleCompte;
    private TypeCompte typeCompte;
    private List<GetCompteClientResponses> titulairesCompte;
    private List<GetTransictionResponses> transactions;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetCompteClientResponses {
        private long idClient;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetTransictionResponses{
        private long id;
        private double montant;
        private TypeTransaction typeTransaction;
        private TypeSource typeSource;
        private long idSource ;

    }

}
