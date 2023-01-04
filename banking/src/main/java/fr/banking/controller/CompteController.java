package fr.banking.controller;

import fr.banking.services.ClientService;
import fr.banking.services.CompteService;
import fr.banking.services.dto.compte.PostCardRequest;
import fr.banking.services.dto.compte.PostCompteRequest;
import fr.banking.services.dto.compte.PostCompteResponses;
import fr.banking.services.dto.compte.PostPaiementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("comptes")
public class CompteController {
    @Autowired
    private CompteService compteService;

    @GetMapping
    private ResponseEntity getCompte(@RequestParam("identifiantClient") long identifiantClient) {
        if (identifiantClient == -1) {
            return ResponseEntity
                    .badRequest()
                    .body(new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED)); //Temporaire à changer plus tard
        } else {
            return ResponseEntity.ok().body(this.compteService.getCompte(identifiantClient));
        }
    }

    @PostMapping
    private ResponseEntity createCompte(@RequestBody PostCompteRequest request) {
        return ResponseEntity.created(null).body(this.compteService.postCompte(request));
    }

    @GetMapping("/{iban}/cartes")
    private ResponseEntity getCartes(@PathVariable String iban) {
        return ResponseEntity.created(null).body(this.compteService.getCartes(iban));
    }

    @PostMapping("/{iban}/cartes")
    private ResponseEntity createCarte(@PathVariable String iban, @RequestBody PostCardRequest postCardRequest){
        return ResponseEntity.created(null).body(this.compteService.createCarte(iban, postCardRequest));
    }

    @PostMapping("/{iban}/cartes/{numeroCarte}/paiement")
    private ResponseEntity paiementCarte(@PathVariable String iban, @PathVariable String numeroCarte,
                                         @RequestBody PostPaiementRequest postPaiementRequest){
        return ResponseEntity.created(null).body(this.compteService.paiementCarte(iban, numeroCarte, postPaiementRequest));

    }
}
