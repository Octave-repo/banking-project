package fr.banking.controller;

import fr.banking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
