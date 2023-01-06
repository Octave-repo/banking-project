package fr.banking.services;

import fr.banking.entities.*;
import fr.banking.repository.CarteRepository;
import fr.banking.repository.ClientRepository;
import fr.banking.repository.TransactionRepository;
import fr.banking.services.dto.compte.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import fr.banking.repository.CompteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CompteService {
    private static final AtomicLong atom = new AtomicLong();

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CarteRepository carteRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public List<GetCompteResponses> getCompte (Long id){
        ClientEntity clients = this.clientRepository.findClientEntityById(id);
        if (clients == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client non trouvé");
        }
        List<CompteEntity> compte = clients.getComptes();
        if (compte.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte non trouvé");
        }
        return compte
                .stream()
                .map(co -> GetCompteResponses.builder()
                        .Iban(co.getIBAN())
                        .solde(co.getSolde())
                        .intituleCompte(co.getIntituleCompte())
                        .typeCompte(co.getTypeCompte())
                        .titulairesCompte(co.getClients().stream()
                                .map(client -> GetCompteResponses.GetCompteClientResponses.builder()
                                        .idClient(client.getId())
                                        .build()).collect(Collectors.toList()))
                        .transactions(co.getTransactions().stream()
                                .map(tr -> GetCompteResponses.GetTransictionResponses.builder()
                                        .id(tr.getId())
                                        .montant(tr.getValeur())
                                        .typeTransaction(tr.getTypeTransaction())
                                        .typeSource(tr.getTypeSource())
                                        .idSource(tr.getId())
                                        .build()).collect(Collectors.toList()))
                        .build()).collect(Collectors.toList());
    }
    private String createIban(){
        StringBuilder iban = new StringBuilder();
        Long nCompte = atom.getAndIncrement()+ 10000000000L;
        iban.append("FR76");
        iban.append("30003");
        iban.append("02054");
        iban.append(nCompte);
        iban.append(97 -  (( 89 * 30003  +15 * 2054 + 3 * nCompte) % 97 ));
        return iban.toString();
    }

    private List<ClientEntity> recupClient (List<Long> listId){
        List<ClientEntity> listClient = new ArrayList<>();
        for (Long id :listId){
            ClientEntity client = this.clientRepository.findClientEntityById(id);
            if (client == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client non trouvé");
            }
            listClient.add(client);
        }
        return listClient;
    }

    public PostCompteResponses postCompte (PostCompteRequest compteCreate){
        List<ClientEntity> listClient = recupClient(compteCreate.getAllId());
        CompteEntity compteSave = CompteEntity.builder()
                .iBAN(createIban())
                //On met un peu d'argent pour pouvoir faire des transactions
                .solde(1000)
                .intituleCompte(compteCreate.getIntituleCompte())
                .typeCompte(compteCreate.getTypeCompte())
                .clients(listClient)
                .cartes(null)
                .dateCreation(Timestamp.valueOf(LocalDateTime.now()))
                .transactions(null)
                .build();
        this.compteRepository.save(compteSave);
        for (ClientEntity client : listClient){
            client.getComptes().add(compteSave);
            this.clientRepository.save(client);
        }
        return buildPostCompte(compteSave);
    }

    public List<GetCompteResponses.GetCompteClientResponses> getAllIdClients(CompteEntity compte) {
        List<GetCompteResponses.GetCompteClientResponses> listId=new ArrayList<>();
        for (ClientEntity c:compte.getClients()){
            listId.add(new GetCompteResponses.GetCompteClientResponses(c.getId()));
        }
        return listId;
    }

    public List<GetCardResponse> getCartes(String iban) {
        List<GetCardResponse> listCard=new ArrayList<>();
        CompteEntity compte = this.compteRepository.findCompteEntityByiBAN(iban);
        if (compte == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte non trouvé");
        }
        for (CarteEntity c:compte.getCartes()){
            if (c == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carte non trouvé");
            }
            listCard.add(GetCardResponse.builder()
                    .numeroCarte(c.getCardNumber())
                    .dateExpiration(c.getExpirationDate())
                            .titulaireCarte(GetCardResponse.Titulaire.builder()
                                    .idClient(c.getCompte().getIBAN())
                                    .build())
                    .build());
        }
        return listCard;
    }
    private PostCompteResponses buildPostCompte (CompteEntity compte){
        return PostCompteResponses.builder()
                .intituleCompte(compte.getIntituleCompte())
                .typeCompte(compte.getTypeCompte())
                .titulairesCompte(getAllIdClients(compte))
                .iBAN(compte.getIBAN())
                .solde(compte.getSolde())
                .build();
    }

    public PostCardResponse createCarte(String iban, PostCardRequest postCardRequest) {
        CompteEntity compte = compteRepository.findCompteEntityByiBAN(iban);
        if (compte == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte non trouvé");
        }
        if (compte.getCartes().size() >= 2){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le comptes possède déjà 2 cartes");
        }
        LocalDateTime now = LocalDateTime.now();
        CarteEntity carte = CarteEntity.builder()
                //On génère le numéro de carte basé sur le moment de la création
                //On ajoute un 0 devant si le mois est inférieur à 10
                .moisExpiration((now.getMonthValue() >= 10 ? "" : "0") + now.getMonthValue())
                //On enlève les deux premiers chiffres de l'année
                .anneeExpiration(String.valueOf(now.getYear() + 3).substring(2))
                .password(postCardRequest.getCode())
                .compte(compte)
                .build();
        carteRepository.save(carte);
        compte.getCartes().add(carte);
        compteRepository.save(compte);
        return buildPostCard(carte);
    }
    private PostCardResponse buildPostCard (CarteEntity carte){
        return PostCardResponse.builder()
                .numeroCarte(carte.getCardNumber())
                .dateExpiration(carte.getExpirationDate())
                .titulaireCarte(carte.getCompte().getIBAN())
                .build();
    }

    public PostPaiementResponse paiementCarte(String iban, String numeroCarte, PostPaiementRequest postPaiementRequest) {
        CompteEntity compte = compteRepository.findCompteEntityByiBAN(iban);
        CarteEntity carte = carteRepository.findCarteEntityByCardNumber(numeroCarte);
        if (compte == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte non trouvé");
        if (carte == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carte non trouvé");
        if (carte.getCompte().getIBAN().equals(iban)) {
            if (compte.getSolde() >= postPaiementRequest.getMontant()) {
                compte.setSolde(compte.getSolde() - postPaiementRequest.getMontant());
                compteRepository.save(compte);
                TransactionEntity transaction = TransactionEntity.builder()
                        .valeur(postPaiementRequest.getMontant())
                        .typeTransaction(TypeTransaction.DEBIT)
                        .typeSource(TypeSource.CARTE)
                        .emetteur(compte)
                        .date(Timestamp.valueOf(LocalDateTime.now()))
                        .build();
                transactionRepository.save(transaction);
                compte.getTransactions().add(transaction);
                compteRepository.save(compte);
                return buildPostPaiement(transaction);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solde insuffisant");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Carte non associée au compte");
        }
    }

    private PostPaiementResponse buildPostPaiement(TransactionEntity transaction) {
        return PostPaiementResponse.builder()
                .idTransaction(transaction.getId())
                .typeTransaction(transaction.getTypeTransaction())
                .dateCreation(transaction.getDate())
                .montant(transaction.getValeur())
                .build();
    }
}
