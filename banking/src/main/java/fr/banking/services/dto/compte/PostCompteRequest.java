package fr.banking.services.dto.compte;

import fr.banking.entities.TypeCompte;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCompteRequest {
    private String intituleCompte;
    private TypeCompte typeCompte;
    private List<GetCompteClientRequest> titulairesCompte;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetCompteClientRequest {
        private Long idClient;
    }

    public List<Long> getAllId(){
        ArrayList<Long> out = new ArrayList<>();
        for(GetCompteClientRequest client : titulairesCompte){
            out.add(client.getIdClient());
        }
        return out;
    }
}
