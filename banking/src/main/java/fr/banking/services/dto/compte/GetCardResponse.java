package fr.banking.services.dto.compte;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetCardResponse {
    String numeroCarte;
    String dateExpiration;
    Titulaire titulaireCarte;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Titulaire {
        String idClient;
    }
}
