package fr.banking.services.dto.virement;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostVirementReponse {
    private long idVirement;
    private String dateCreation;
    private List<Transaction> transactions;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Transaction {
        private long id;
        private double montant;
        private String typeTransaction;
        private String typeSource;
        private long idSource;
    }
}
