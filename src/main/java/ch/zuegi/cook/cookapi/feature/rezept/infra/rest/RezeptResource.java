package ch.zuegi.cook.cookapi.feature.rezept.infra.rest;

import ch.zuegi.cook.cookapi.MeineRefactoringKlasse;
import ch.zuegi.cook.cookapi.feature.rezept.service.RezeptApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/rezept")
public class RezeptResource {

    @Autowired
    RezeptApplicationService rezeptApplicationService;

    @Operation(summary = "Erstellt ein neues Rezept.")
    @PostMapping(value ="/refactor")
    public ResponseEntity.BodyBuilder create(@RequestBody @Valid MeineRefactoringKlasse meineRefactoringKlasse) {
        log.info("create {} ", meineRefactoringKlasse.toString());
        rezeptApplicationService.erstelle(meineRefactoringKlasse);

        return ResponseEntity.ok();
    }

}
