package fr.banking.controller;

import fr.banking.services.ClientService;
import fr.banking.services.CompteService;
import fr.banking.services.dto.compte.PostCompteRequest;
import fr.banking.services.dto.compte.PostCompteResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;

public class CompteController {
    @Autowired
    private CompteService compteService;

    @GetMapping
    private ResponseEntity getCompte(@RequestParam("identifiantClient") Long id){
        if(id == null ) {
            return ResponseEntity
                    .badRequest()
                    .body(new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED)); //Temporaire à changer plus tard
        }
        else{
            return ResponseEntity.ok().body(this.compteService.getCompte(id));
        }
    }

    @PostMapping
    private ResponseEntity<PostCompteResponses> createCompte(@RequestBody PostCompteRequest request) {
        return ResponseEntity.created(null).body(this.compteService.postCompte(request));
    }

}
