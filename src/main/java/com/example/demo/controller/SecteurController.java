package com.example.demo.controller;

import com.example.demo.entities.Famille;
import com.example.demo.entities.Secteur;
import com.example.demo.service.SecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/secteur")
@CrossOrigin(origins = "http://localhost:4200")
public class SecteurController {

    @Autowired
    private SecteurService secteurService;

    @GetMapping
    public ResponseEntity<List<Secteur>> getAllSecteurs() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Secteur>> secteursFuture = secteurService.getAllSecteurs();
        List<Secteur> secteurs = secteursFuture.get();
        return ResponseEntity.ok(secteurs);
    }


    @GetMapping("/{refSecteur}")
    public ResponseEntity<Secteur> getSecteurByRef(@PathVariable String refSecteur) {
        Secteur secteur = secteurService.getSecteurByRef(refSecteur);
        if (secteur != null) {
            return ResponseEntity.ok(secteur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Secteur> createSecteur(@RequestBody Secteur secteur) {
        Secteur createdSecteur = secteurService.createSecteur(secteur);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSecteur);
    }

    @PutMapping("/{refSecteur}")
    public ResponseEntity<Secteur> updateSecteur(@PathVariable String refSecteur, @RequestBody Secteur secteur) {
        Secteur updatedSecteur = secteurService.updateSecteur(refSecteur, secteur);
        if (updatedSecteur != null) {
            return ResponseEntity.ok(updatedSecteur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{refSecteur}")
    public ResponseEntity<Void> deleteSecteur(@PathVariable String refSecteur) {
        boolean deleted = secteurService.deleteSecteur(refSecteur);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/count")
    public CompletableFuture<Integer> getMachineCount() {
        return secteurService.getAllSecteurs()
            .thenApply(machineList -> machineList.size());
    }

}





