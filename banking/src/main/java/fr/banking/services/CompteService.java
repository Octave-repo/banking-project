package fr.banking.services;

import ch.qos.logback.core.net.server.Client;
import fr.banking.entities.ClientEntity;
import fr.banking.entities.CompteEntity;
import fr.banking.repository.ClientRepository;
import fr.banking.services.dto.compte.PostCompteRequest;
import fr.banking.services.dto.compte.PostCompteResponses;
import org.springframework.beans.factory.annotation.Autowired;
import fr.banking.repository.CompteRepository;
import fr.banking.services.dto.compte.GetCompteResponses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class CompteService {
    private AtomicLong atom;

    @Autowired
    private CompteRepository compteRepository;
    private ClientRepository clientRepository;

    public List<GetCompteResponses> getCompte (Long id){
        return this.compteRepository.findByClientsId(id)
                .stream()
                .map(co -> GetCompteResponses.builder()
                        .iBAN(co.getIBAN())
                        .solde(co.getSolde())
                        .intituleCompte(co.getIntituleCompte())
                        .typeCompte(co.getTypeCompte())
                        .titulairesCompte(co.getClients().stream().map(client ->
                                        GetCompteResponses.GetCompteClientResponses
                                                .builder()
                                                .id(client.getId())
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
        StringBuilder iban=new StringBuilder();
        Long nCompte=atom.getAndIncrement()+ 10000000000L;
        iban.append("FR76 ");
        iban.append("30003 ");
        iban.append("02054 ");
        iban.append("nCompte ");
        iban.append(valueOf(97 -  (89 * 30003  +15 * 2054 + 3 *nCompte % 97 )));
        return iban.toString();
    }

    private List<ClientEntity> recupClient (List<PostCompteRequest.GetCompteClientRequest> listId){
        List<ClientEntity> listClient=new ArrayList<>();
        for (PostCompteRequest.GetCompteClientRequest id :listId){
            listClient.add(clientRepository.findClientEntityById(id.getId()));
        }
        return listClient;
    }
    public PostCompteResponses postCompte (PostCompteRequest CompteCreate){
        CompteEntity compteSave = this.compteRepository.save(CompteEntity.builder()
                .iBAN(createIban())
                .solde(0)
                .intituleCompte(CompteCreate.getIntituleCompte())
                .typeCompte(CompteCreate.getTypeCompte())
                .clients(recupClient(CompteCreate.getIdTitulairesCompte()))
                .cartes(null)
                .dateCreation(new Date())
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
