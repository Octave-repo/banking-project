package fr.banking.controller;

import fr.banking.controller.common.HttpErreurFonctionnelle;
import fr.banking.services.ClientService;
import fr.banking.services.dto.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//TODO Gérer les exceptions !
@RestController
@RequestMapping("clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    private ResponseEntity getClients(@RequestParam("nom") String nom, @RequestParam("prenom") String prenom){
        if(nom.isEmpty() || prenom.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new HttpErreurFonctionnelle("Les données en entrées sont incorrectes"));
        }
        else{
            try{
            List<GetClientResponse> response = this.clientService.getClientByNameAndLastName(nom, prenom);
            if (!response.isEmpty())
                return ResponseEntity.ok().body(response);
            else
                return ResponseEntity.noContent().build();
            } catch (Exception e){
                return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
            }
        }
    }

    @PostMapping
    private ResponseEntity createClient(@RequestBody PostClientRequest postClientRequest){
        //On vérifie que les données en entrées sont correctes
        if (postClientRequest == null || postClientRequest.getNom() == null || postClientRequest.getPrenom() == null ||
                postClientRequest.getDateNaissance() == null || postClientRequest.getAdressePostale() ==null ||
                postClientRequest.getTelephone() == null){
            return ResponseEntity.badRequest().body(
                    new HttpErreurFonctionnelle("Les donnnées en entrée du service sont non renseignes ou incorrectes"));
        }
        else{
            try{
                PostClientResponse response = this.clientService.createClient(postClientRequest);
                return ResponseEntity.ok().body(response);
            } catch (Exception e){
                return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
            }
        }
    }

    @PutMapping
    private ResponseEntity updateClient(@RequestBody PutClientRequest putClientRequest){
        if (putClientRequest == null  || putClientRequest.getId() <= 0 || putClientRequest.getNom() == null ||
                putClientRequest.getPrenom() == null || putClientRequest.getDateNaissance() == null ||
                putClientRequest.getAdressePostale() == null || putClientRequest.getTelephone() == null){
            return ResponseEntity.badRequest().body(
                    new HttpErreurFonctionnelle("Les donnnées en entrée du service sont non renseignes ou incorrectes"));
        }
        else{
            try{
                PutClientResponse response = this.clientService.updateClient(putClientRequest);
                return ResponseEntity.ok().body(response);
            } catch (ResponseStatusException e){
                if (e.getStatus().value() == 404)
                    return ResponseEntity.notFound().build();
                else
                    return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
            }
            catch (Exception e){
                return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
            }
        }
    }
}
