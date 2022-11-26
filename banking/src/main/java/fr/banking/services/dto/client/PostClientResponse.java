package fr.banking.services.dto.client;

import lombok.*;

import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostClientResponse {
    private long id;
    private String nom;
    private String prenom;
    private Timestamp dateNaissance;
    private String telephone;
    private String adressePostale;
    private Timestamp dateCreation;
}
