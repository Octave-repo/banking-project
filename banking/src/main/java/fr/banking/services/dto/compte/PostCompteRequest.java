package fr.banking.services.dto.compte;

import fr.banking.entities.ClientEntity;
import fr.banking.entities.TypeCompte;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCompteRequest {
    private String intituleCompte;
    private TypeCompte typeCompte;
    private List<GetCompteClientRequest> idTitulairesCompte;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetCompteClientRequest {
        private Long id;
    }
}
