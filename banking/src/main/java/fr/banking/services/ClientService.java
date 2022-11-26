package fr.banking.services;

import fr.banking.entities.ClientEntity;
import fr.banking.repository.ClientRepository;
import fr.banking.services.dto.client.GetClientResponse;
import fr.banking.services.dto.client.PostClientRequest;
import fr.banking.services.dto.client.PostClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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


}
