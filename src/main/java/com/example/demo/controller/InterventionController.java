package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Fournisseur;
import com.example.demo.entities.Intervention;
import com.example.demo.entities.Secteur;
import com.example.demo.entities.users;
import com.example.demo.service.InterventionService;
import com.example.demo.service.SecteurService;

@RestController
@RequestMapping("/intervention")
@CrossOrigin(origins = "http://localhost:4200")

public class InterventionController {
	@Autowired
    private InterventionService interventionService;
	 @GetMapping("/{page}")
	  public ResponseEntity<List<Intervention>> getInterventionByPage(@PathVariable int page) throws ExecutionException, InterruptedException {
	      int pageSize = 5; // Nombre d'utilisateurs à afficher par page
	      int startIndex = (page - 1) * pageSize; // Indice de départ pour la pagination
	      CompletableFuture<List<Intervention>> usersFuture = interventionService.getAllInterventions();
	      List<Intervention> allUsers = usersFuture.get();

	      List<Intervention> usersPage = new ArrayList<>();
	      for (int i = startIndex; i < Math.min(startIndex + pageSize, allUsers.size()); i++) {
	          usersPage.add(allUsers.get(i));
	      }

	      return ResponseEntity.ok(usersPage);
	  }


    @GetMapping
    public ResponseEntity<List<Intervention>> getAllInterventions() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Intervention>> secteursFuture = interventionService.getAllInterventions();
        List<Intervention> secteurs = secteursFuture.get();
        return ResponseEntity.ok(secteurs);
    }
    @DeleteMapping("/{dataBon}")
    public ResponseEntity<Void> deleteIntervention(@PathVariable String dataBon) {
        boolean deleted = interventionService.deleteIntervention(dataBon);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/count")
    public CompletableFuture<Integer> getMachineCount() {
        return interventionService.getAllInterventions()
            .thenApply(machineList -> machineList.size());
    }
    @GetMapping("/ref/{dataBon}")
    public ResponseEntity<Intervention> getInterventionByDataBon(@PathVariable("dataBon") Long dataBon) {
       Intervention intervention = interventionService.getinterventionByBon(dataBon);
        if (intervention != null) {
            return ResponseEntity.ok(intervention);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
  }
