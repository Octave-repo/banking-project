package fr.banking.services;

import fr.banking.entities.ClientEntity;
import fr.banking.repository.ClientRepository;
import fr.banking.services.dto.client.GetClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
