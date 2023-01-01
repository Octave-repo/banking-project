package fr.banking.controller;

import fr.banking.services.ClientService;
import fr.banking.services.dto.client.PostClientRequest;
import fr.banking.services.dto.client.PutClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    private ResponseEntity getClients(@RequestParam("nom") String nom, @RequestParam("prenom") String prenom){
        if(nom == null || prenom == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED)); //Temporaire à changer plus tard
        }
        else{
            return ResponseEntity.ok().body(this.clientService.getClientByNameAndLastName(nom, prenom));
        }
    }

    @PostMapping
    private ResponseEntity createClient(@RequestBody PostClientRequest postClientRequest){
        return ResponseEntity.created(null).body(this.clientService.createClient(postClientRequest));
    }

    @PutMapping
    private ResponseEntity updateClient(@RequestBody PutClientRequest putClientRequest){
        return ResponseEntity.created(null).body(this.clientService.updateClient(putClientRequest));
    }
}
