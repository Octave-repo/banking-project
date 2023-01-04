package fr.banking.services;

import fr.banking.entities.*;
import fr.banking.repository.CompteRepository;
import fr.banking.repository.TransactionRepository;
import fr.banking.repository.VirementRepository;
import fr.banking.services.dto.virement.PostVirementReponse;
import fr.banking.services.dto.virement.PostVirementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VirementService {
    @Autowired
    private VirementRepository virementRepository;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public PostVirementReponse createVirement(PostVirementRequest postVirementRequest) {
        CompteEntity emetteur = compteRepository.findCompteEntityByiBAN(postVirementRequest.getIbanCompteEmetteur());
        CompteEntity beneficiaire = compteRepository.findCompteEntityByiBAN(postVirementRequest.getIbanCompteBeneficiaire());
        if (emetteur == null || beneficiaire == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte non trouvé");
        }
        if (emetteur.getSolde() < postVirementRequest.getMontant()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solde insuffisant");
        }
        TransactionEntity transactionEmetteur =
                TransactionEntity.builder()
                        .raison(postVirementRequest.getLibelleVirement())
                        .emetteur(emetteur)
                        .valeur(postVirementRequest.getMontant())
                        .typeTransaction(TypeTransaction.DEBIT)
                        .typeSource(TypeSource.VIREMENT)
                        .date(Timestamp.valueOf(LocalDateTime.now()))
                        .build();
        TransactionEntity transactionBeneficiaire =
                TransactionEntity.builder()
                        .raison(postVirementRequest.getLibelleVirement())
                        .emetteur(beneficiaire)
                        .valeur(postVirementRequest.getMontant())
                        .typeTransaction(TypeTransaction.CREDIT)
                        .typeSource(TypeSource.VIREMENT)
                        .date(Timestamp.valueOf(LocalDateTime.now()))
                        .build();
        VirementEntity virementSave =
                VirementEntity.builder()
                        .dateCreation(Timestamp.valueOf(LocalDateTime.now()))
                        .transactionEmmeteur(transactionEmetteur)
                        .transactionRecepteur(transactionBeneficiaire)
                        .build();
        emetteur.setSolde(emetteur.getSolde() - postVirementRequest.getMontant());
        beneficiaire.setSolde(beneficiaire.getSolde() + postVirementRequest.getMontant());
        transactionRepository.save(transactionEmetteur);
        transactionRepository.save(transactionBeneficiaire);
        virementRepository.save(virementSave);
        emetteur.getTransactions().add(transactionEmetteur);
        beneficiaire.getTransactions().add(transactionBeneficiaire);
        compteRepository.save(emetteur);
        compteRepository.save(beneficiaire);
        return buildPostVirementReponse(virementSave, transactionEmetteur, transactionBeneficiaire);
    }

    private PostVirementReponse buildPostVirementReponse(VirementEntity virementSave, TransactionEntity transactionEmetteur, TransactionEntity transactionBeneficiaire) {
        return PostVirementReponse.builder()
                .idVirement(virementSave.getId())
                .dateCreation(virementSave.getDateCreation().toString())
                .transactions(
                        List.of(
                                PostVirementReponse.Transaction.builder()
                                        .id(transactionEmetteur.getId())
                                        .montant(transactionEmetteur.getValeur())
                                        .typeTransaction(transactionEmetteur.getTypeTransaction().toString())
                                        .typeSource(transactionEmetteur.getTypeSource().toString())
                                        .idSource(transactionEmetteur.getId())
                                        .build(),
                                PostVirementReponse.Transaction.builder()
                                        .id(transactionBeneficiaire.getId())
                                        .montant(transactionBeneficiaire.getValeur())
                                        .typeTransaction(transactionBeneficiaire.getTypeTransaction().toString())
                                        .typeSource(transactionBeneficiaire.getTypeSource().toString())
                                        .idSource(transactionBeneficiaire.getId())
                                        .build()
                        )
                )
                .build();
    }
}
