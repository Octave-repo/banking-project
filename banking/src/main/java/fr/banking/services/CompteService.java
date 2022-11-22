package fr.banking.services;

import org.springframework.beans.factory.annotation.Autowired;
import fr.banking.repository.CompteRepository;
import fr.banking.services.dto.compte.GetCompteResponses;

import java.util.List;
import java.util.stream.Collectors;

public class CompteService {
    @Autowired
    private CompteRepository compteRepository;

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
}
