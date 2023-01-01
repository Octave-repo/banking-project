package fr.banking.services;

import fr.banking.entities.ClientEntity;
import fr.banking.repository.ClientRepository;
import fr.banking.services.dto.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<GetClientResponse> getClientByNameAndLastName(String nom, String prenom){
        return this.clientRepository.findClientEntityByNomAndPrenom(nom, prenom)
                .stream()
                .map(c -> GetClientResponse.builder()
                        .id(c.getId()).nom(c.getNom())
                        .prenom(c.getPrenom()).dateNaissance(c.getDateNaissance())
                        .telephone(c.getTelephone()).adresse(c.getAdresse())
                        .dateCreation(c.getDateCreation()).build())
                .collect(Collectors.toList());
    }

    public PostClientResponse createClient(PostClientRequest postClientRequest) {
        ClientEntity clientSave = this.clientRepository.save(
                ClientEntity.builder().nom(postClientRequest.getNom())
                        .prenom(postClientRequest.getPrenom())
                        .dateNaissance(postClientRequest.getDateNaissance())
                        .adresse(postClientRequest.getAdressePostale())
                        .telephone(postClientRequest.getTelephone())
                        .dateCreation(Timestamp.valueOf(LocalDateTime.now()))
                        .build());

        return buildCreateClientResponse(clientSave);
    }


    private PostClientResponse buildCreateClientResponse(ClientEntity clientSave) {
        return PostClientResponse.builder()
                .id(clientSave.getId())
                .nom(clientSave.getNom())
                .prenom(clientSave.getPrenom())
                .dateNaissance(clientSave.getDateNaissance())
                .telephone(clientSave.getTelephone())
                .adressePostale(clientSave.getAdresse())
                .dateCreation(clientSave.getDateCreation()).build();
    }

    public PutClientResponse updateClient(PutClientRequest putClientRequest){
        ClientEntity clientSave = this.clientRepository.save(
                ClientEntity.builder().id(putClientRequest.getId())
                        .nom(putClientRequest.getNom())
                        .prenom(putClientRequest.getPrenom())
                        .dateNaissance(putClientRequest.getDateNaissance())
                        .adresse(putClientRequest.getAdressePostale())
                        .telephone(putClientRequest.getTelephone())
                        .build());

        return buildPutClientResponse(clientSave);
    }

    private PutClientResponse buildPutClientResponse(ClientEntity clientSave) {
        return PutClientResponse.builder()
                .id(clientSave.getId())
                .nom(clientSave.getNom())
                .prenom(clientSave.getPrenom())
                .dateNaissance(clientSave.getDateNaissance())
                .telephone(clientSave.getTelephone())
                .adressePostale(clientSave.getAdresse())
                .dateModification(Timestamp.from(Instant.now())).build();
    }
}
