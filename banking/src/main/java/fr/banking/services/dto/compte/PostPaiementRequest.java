package fr.banking.services.dto.compte;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostPaiementRequest {
    double montant;
}
