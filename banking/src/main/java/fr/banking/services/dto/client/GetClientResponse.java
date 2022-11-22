package fr.banking.services.dto.client;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetClientResponse {
    private long id;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String telephone;
    private String adresse;
    private Date dateCreation;
}
