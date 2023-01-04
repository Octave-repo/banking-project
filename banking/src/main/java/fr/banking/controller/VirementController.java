package fr.banking.controller;

import fr.banking.services.VirementService;
import fr.banking.services.dto.virement.PostVirementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("virements")
public class VirementController {
    @Autowired
    private VirementService virementService;
    @PostMapping

    private ResponseEntity createVirement(@RequestBody PostVirementRequest postVirementRequest){
        return ResponseEntity.created(null).body(this.virementService.createVirement(postVirementRequest));
    }
}
