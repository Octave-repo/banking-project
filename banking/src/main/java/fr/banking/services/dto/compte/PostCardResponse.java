package fr.banking.services.dto.compte;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCardResponse {
    String titulaireCarte;
    String numeroCarte;
    String dateExpiration;
}
