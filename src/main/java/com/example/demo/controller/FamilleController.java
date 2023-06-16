package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import com.example.demo.entities.Emplacement;
import com.example.demo.entities.Famille;
import com.example.demo.entities.Fournisseur;
import com.example.demo.service.FamilleService;

@RestController
@RequestMapping("/famille")
@CrossOrigin(origins = "http://localhost:4200")
public class FamilleController {

    @Autowired
    private FamilleService familleService;

    @GetMapping("/{refFamille}")
    public ResponseEntity<Famille> getFamilleByRef(@PathVariable String refFamille) {
        Famille famille = familleService.getFamilleByRef(refFamille);
        if (famille != null) {
            return ResponseEntity.ok(famille);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/count")
    public CompletableFuture<Integer> getEquipementCount() {
        return familleService.getAllFamilles()
            .thenApply(machineList -> machineList.size());
    }
    @GetMapping("/page/{page}")
	  public ResponseEntity<List<Famille>> getFamilleByPage(@PathVariable int page) throws ExecutionException, InterruptedException {
	      int pageSize = 5; // Nombre d'utilisateurs à afficher par page
	      int startIndex = (page - 1) * pageSize; // Indice de départ pour la pagination
	      CompletableFuture<List<Famille>> usersFuture = familleService.getAllFamilles();
	      List<Famille> allUsers = usersFuture.get();

	      List<Famille> usersPage = new ArrayList<>();
	      for (int i = startIndex; i < Math.min(startIndex + pageSize, allUsers.size()); i++) {
	          usersPage.add(allUsers.get(i));
	      }

	      return ResponseEntity.ok(usersPage);
	  }

    @GetMapping
    public ResponseEntity<List<Famille>> getAllFamilles() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Famille>> secteursFuture = familleService.getAllFamilles();
        List<Famille> secteurs = secteursFuture.get();
        return ResponseEntity.ok(secteurs);
    }

    @DeleteMapping("/{refFamille}")
    public ResponseEntity<Void> deleteFamille(@PathVariable String refFamille) {
        boolean deleted = familleService.deleteFamille(refFamille);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/totalItems")
    public ResponseEntity<Long> getTotalItems() {
        long totalItems = familleService.getTotalItems();
        return ResponseEntity.ok(totalItems);
    }

    @PostMapping
    public ResponseEntity<Famille> createFamille(@RequestBody Famille famille) {
        Famille createdFamille = familleService.createFamille(famille);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFamille);
    }

    @PutMapping("/{refFamille}")
    public ResponseEntity<Famille> updateFamille(@PathVariable String refFamille, @RequestBody Famille famille) {
        Famille updatedFamille = familleService.updateFamille(refFamille, famille);
        if (updatedFamille != null) {
            return ResponseEntity.ok(updatedFamille);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
