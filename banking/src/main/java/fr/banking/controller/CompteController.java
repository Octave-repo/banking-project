package fr.banking.controller;

import fr.banking.controller.common.HttpErreurFonctionnelle;
import fr.banking.services.CompteService;
import fr.banking.services.dto.compte.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("comptes")
public class CompteController {
    @Autowired
    private CompteService compteService;

    @GetMapping
    private ResponseEntity getCompte(@RequestParam("identifiantClient") long identifiantClient) {
        if (identifiantClient < 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new HttpErreurFonctionnelle("Les données en entrées sont incorrectes")); //Temporaire à changer plus tard
        } else {
            try{
                List<GetCompteResponses> compteResponses = this.compteService.getCompte(identifiantClient);
                return ResponseEntity.ok().body(compteResponses);
            } catch (ResponseStatusException e) {
                if (e.getStatus().equals(HttpStatus.NOT_FOUND)){
                    return  ResponseEntity.notFound().build();
                } else{
                    return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
                }
            } catch (Exception e){
                return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
            }
        }
    }

    @PostMapping
    private ResponseEntity createCompte(@RequestBody PostCompteRequest request) {
        if (request.getIntituleCompte() == null || request.getTypeCompte() == null
            || request.getTitulairesCompte() == null){
            return ResponseEntity.badRequest().body(new HttpErreurFonctionnelle("Les données en entrées sont incorrectes"));
        }
        try{
            PostCompteResponses postCompteResponses = this.compteService.postCompte(request);
            return ResponseEntity.created(null).body(postCompteResponses);
        } catch (ResponseStatusException e){
            if (e.getStatus().equals(HttpStatus.NOT_FOUND)){
                return ResponseEntity.badRequest().body(new HttpErreurFonctionnelle("Client(s) non trouvé"));
            }
            else
                return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
        }
    }

    @GetMapping("/{iban}/cartes")
    private ResponseEntity getCartes(@PathVariable String iban) {
        try{
            List<GetCardResponse> carteResponses = this.compteService.getCartes(iban);
            if (carteResponses.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(carteResponses);
        } catch (ResponseStatusException e){
            if (e.getStatus().equals(HttpStatus.NOT_FOUND)){
                return ResponseEntity.badRequest().body(new HttpErreurFonctionnelle(e.getReason()));
            }
            else
                return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
        }
    }


    @PostMapping("/{iban}/cartes")
    private ResponseEntity createCarte(@PathVariable String iban, @RequestBody PostCardRequest postCardRequest){
        if (postCardRequest.getTitulaireCarte() == null){
            return ResponseEntity.badRequest().body(new HttpErreurFonctionnelle("Les données en entrées sont incorrectes"));
        }else{
            try{
                PostCardResponse postCardResponse = this.compteService.createCarte(iban, postCardRequest);
                return ResponseEntity.created(null).body(postCardResponse);
            } catch (ResponseStatusException e){
                if (e.getStatus().equals(HttpStatus.NOT_FOUND) || e.getStatus().equals(HttpStatus.BAD_REQUEST)){
                    return ResponseEntity.badRequest().body(new HttpErreurFonctionnelle(e.getReason()));
                }
                else
                    return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
            }
            catch (Exception e){
                return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
            }
        }
    }

    @PostMapping("/{iban}/cartes/{numeroCarte}/paiement")
    private ResponseEntity paiementCarte(@PathVariable String iban, @PathVariable String numeroCarte,
                                         @RequestBody PostPaiementRequest postPaiementRequest){
        if (postPaiementRequest.getMontant() < 0){
            return ResponseEntity.badRequest().body(
                    new HttpErreurFonctionnelle("Les données en entrées sont incorrectes, le montant doit être supérieur à 0."));
        }
        try{
            PostPaiementResponse postPaiementResponse = this.compteService.paiementCarte(iban, numeroCarte, postPaiementRequest);
            return ResponseEntity.created(null).body(postPaiementResponse);
        } catch (ResponseStatusException e){
            return ResponseEntity.badRequest().body(new HttpErreurFonctionnelle(e.getReason()));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
        }
    }
}
