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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Classification;
import com.example.demo.entities.Emplacement;
import com.example.demo.entities.Intervention;
import com.example.demo.entities.users;
import com.example.demo.service.ClassificationService;
import com.example.demo.service.EmplacemtService;

@RestController
@RequestMapping("/emplacement")
@CrossOrigin(origins = "http://localhost:4200")
public class EmplacementController {
	 @Autowired
	    private EmplacemtService emplacementService;
	 @GetMapping("/ref/{refEmplacement}")
	    public ResponseEntity<Emplacement> getEmplacementByRef(@PathVariable String refEmplacement) {
	        Emplacement secteur = emplacementService.getEmplacementByRef(refEmplacement);
	        if (secteur != null) {
	            return ResponseEntity.ok(secteur);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	   @GetMapping
	    public ResponseEntity<List<Emplacement>> getAllEmplacements() throws ExecutionException, InterruptedException {
	        CompletableFuture<List<Emplacement>> secteursFuture = emplacementService.getAllEmplacements();
	        List<Emplacement> secteurs = secteursFuture.get();
	        return ResponseEntity.ok(secteurs);
	    }
	   @DeleteMapping("/{refEmplacement}")
	    public ResponseEntity<Void> deleteEmplacement(@PathVariable String refEmplacement) {
	        boolean deleted = emplacementService.deleteEmplacement(refEmplacement);
	        if (deleted) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	   @GetMapping("/page/{page}")
		  public ResponseEntity<List<Emplacement>> getEmplacementByPage(@PathVariable int page) throws ExecutionException, InterruptedException {
		      int pageSize = 5; // Nombre d'utilisateurs à afficher par page
		      int startIndex = (page - 1) * pageSize; // Indice de départ pour la pagination
		      CompletableFuture<List<Emplacement>> usersFuture = emplacementService.getAllEmplacements();
		      List<Emplacement> allUsers = usersFuture.get();

		      List<Emplacement> usersPage = new ArrayList<>();
		      for (int i = startIndex; i < Math.min(startIndex + pageSize, allUsers.size()); i++) {
		          usersPage.add(allUsers.get(i));
		      }

		      return ResponseEntity.ok(usersPage);
		  }

	   @PostMapping
	    public ResponseEntity<Emplacement> createemplacement(@RequestBody Emplacement machine) {
	        // Mettre à jour la valeur de ref avec la clé souhaitée
	    //    machine.setRef("votre_clé_primaire");

	        Emplacement createdMachine = emplacementService.createEmplacement(machine);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdMachine);
	    }


	    @GetMapping("/count")
	    public CompletableFuture<Integer> getEquipementCount() {
	        return emplacementService.getAllEmplacements()
	            .thenApply(machineList -> machineList.size());
	    }
	    @PutMapping("/{refEmplacement}")
	    public ResponseEntity<Emplacement> updateEmplacement(@PathVariable String refEmplacement, @RequestBody Emplacement secteur) {
	        Emplacement updatedSecteur = emplacementService.updateEmplacement(refEmplacement,secteur);
	        if (updatedSecteur != null) {
	            return ResponseEntity.ok(updatedSecteur);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}



