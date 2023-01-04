package fr.banking.services.dto.compte;

import fr.banking.entities.TypeTransaction;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostPaiementResponse {
    long idTransaction;
    double montant;
    TypeTransaction typeTransaction;
    Timestamp dateCreation;
}
