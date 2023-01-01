package fr.banking.services.dto.client;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PutClientRequest {
    private long id;
    private String prenom;
    private String nom;
    private Timestamp dateNaissance;
    private String telephone;
    private String adressePostale;
}
