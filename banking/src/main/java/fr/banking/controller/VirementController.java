package fr.banking.controller;

import fr.banking.controller.common.HttpErreurFonctionnelle;
import fr.banking.services.VirementService;
import fr.banking.services.dto.virement.PostVirementReponse;
import fr.banking.services.dto.virement.PostVirementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("virements")
public class VirementController {
    @Autowired
    private VirementService virementService;
    @PostMapping

    private ResponseEntity createVirement(@RequestBody PostVirementRequest postVirementRequest){
        if (postVirementRequest.getMontant() <= 0 ){
            return ResponseEntity.badRequest().body(
                    new HttpErreurFonctionnelle("Les données en entrées sont incorrectes, le montant doit être supérieur à 0."));
        }
        if (postVirementRequest.getLibelleVirement() == null
                || postVirementRequest.getIbanCompteBeneficiaire() == null
                || postVirementRequest.getIbanCompteEmetteur() == null){
            return ResponseEntity.badRequest().body(
                    new HttpErreurFonctionnelle("Les données en entrées sont incorrectes."));
        }
        try{
            PostVirementReponse response = this.virementService.createVirement(postVirementRequest);
            return ResponseEntity.created(null).body(response);
        } catch (ResponseStatusException e){
            if (e.getStatus().equals(HttpStatus.NOT_FOUND)){
                return ResponseEntity.badRequest().body(new HttpErreurFonctionnelle("Compte(s) non trouvé"));
            }
            if (e.getStatus().equals(HttpStatus.BAD_REQUEST)){
                return ResponseEntity.badRequest().body(
                        new HttpErreurFonctionnelle("Solde insuffisant"));
            }
            else
                return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("Une erreur interne a été rencontrée");
        }
    }
}
