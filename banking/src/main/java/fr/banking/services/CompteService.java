package fr.banking.services;

import fr.banking.entities.CarteEntity;
import fr.banking.entities.ClientEntity;
import fr.banking.entities.CompteEntity;
import fr.banking.repository.ClientRepository;
import fr.banking.services.dto.compte.GetCardResponse;
import fr.banking.services.dto.compte.PostCompteRequest;
import fr.banking.services.dto.compte.PostCompteResponses;
import org.springframework.beans.factory.annotation.Autowired;
import fr.banking.repository.CompteRepository;
import fr.banking.services.dto.compte.GetCompteResponses;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CompteService {
    private static final AtomicLong atom = new AtomicLong();

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private ClientRepository clientRepository;

    public List<GetCompteResponses> getCompte (Long id){
        return this.compteRepository.findCompteEntityByClientsId(id)
                .stream()
                .map(co -> GetCompteResponses.builder()
                        .Iban(co.getIBAN())
                        .solde(co.getSolde())
                        .intituleCompte(co.getIntituleCompte())
                        .typeCompte(co.getTypeCompte())
                        .titulairesCompte(co.getClients().stream().map(client ->
                                        GetCompteResponses.GetCompteClientResponses
                                                .builder()
                                                .idClient(client.getId())
                                                .build())
                                .collect(Collectors.toList()))
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
        iban.append("FR76 ");
        iban.append("30003 ");
        iban.append("02054 ");
        iban.append(nCompte).append(" ");
        iban.append(97 -  (( 89 * 30003  +15 * 2054 + 3 * nCompte) % 97 ));
        return iban.toString();
    }

    private List<ClientEntity> recupClient (List<Long> listId){
        List<ClientEntity> listClient = new ArrayList<>();
        for (Long id :listId){
            listClient.add(clientRepository.findClientEntityById(id));
        }
        return listClient;
    }

    public PostCompteResponses postCompte (PostCompteRequest compteCreate){
        CompteEntity compteSave = this.compteRepository.save(CompteEntity.builder()
                .iBAN(createIban())
                .solde(0)
                .intituleCompte(compteCreate.getIntituleCompte())
                .typeCompte(compteCreate.getTypeCompte())
                .clients(recupClient(compteCreate.getAllId())) //TODO Vérifier que les clients existent
                .cartes(null)
                .dateCreation(null)
                .transactions(null)
                .build());
        return buildPostCompte(this.compteRepository.save(compteSave));
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
        for (CarteEntity c:compteRepository.findCompteEntityByiBAN(iban).getCartes()){
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

}
