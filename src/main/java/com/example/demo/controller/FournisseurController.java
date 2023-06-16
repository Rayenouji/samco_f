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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Famille;
import com.example.demo.entities.Fournisseur;
import com.example.demo.entities.Machine;
import com.example.demo.service.FamilleService;
import com.example.demo.service.FirebaseStorageService;
import com.example.demo.service.FournisseurService;
@RestController
@RequestMapping("/fournisseur")
@CrossOrigin(origins = "http://localhost:4200")
public class FournisseurController {

	 @Autowired
	    private FournisseurService fournisseurService;
	 @Autowired
	 private FirebaseStorageService firebaseStorageService;

	  @GetMapping("/{page}")
	  public ResponseEntity<List<Fournisseur>> getFournisseurByPage(@PathVariable int page) throws ExecutionException, InterruptedException {
	      int pageSize = 5; // Nombre d'utilisateurs à afficher par page
	      int startIndex = (page - 1) * pageSize; // Indice de départ pour la pagination
	      CompletableFuture<List<Fournisseur>> usersFuture = fournisseurService.getAllFournisseurs();
	      List<Fournisseur> allUsers = usersFuture.get();

	      List<Fournisseur> usersPage = new ArrayList<>();
	      for (int i = startIndex; i < Math.min(startIndex + pageSize, allUsers.size()); i++) {
	          usersPage.add(allUsers.get(i));
	      }

	      return ResponseEntity.ok(usersPage);
	  }
	 
	 @GetMapping("/{dataId}")
	 
	    public ResponseEntity<Fournisseur> getFournisseurByRef(@PathVariable Long dataId) {
           Fournisseur secteur = fournisseurService.getFournisseurById(dataId);
	        if (secteur != null) {
	            return ResponseEntity.ok(secteur);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	   @GetMapping
	    public ResponseEntity<List<Fournisseur>> getAllFournisseurs() throws ExecutionException, InterruptedException {
	        CompletableFuture<List<Fournisseur>> secteursFuture = fournisseurService.getAllFournisseurs();
	        List<Fournisseur> secteurs = secteursFuture.get();
	        return ResponseEntity.ok(secteurs);
	    }
	   @DeleteMapping("/{dataId}")
	    public ResponseEntity<Void> deleteFourisseur(@PathVariable Long dataId) {
	        boolean deleted = fournisseurService.deletefournisseur(dataId);
	        if (deleted) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	   @GetMapping("/count")
	    public CompletableFuture<Integer> getMachineCount() {
	        return fournisseurService.getAllFournisseurs()
	            .thenApply(machineList -> machineList.size());
	    }

	   @PostMapping
	    public ResponseEntity<Fournisseur> createFournisseur(@RequestPart("fournisseur") Fournisseur fournisseur, @RequestPart("imageFile") MultipartFile imageFile) {
	        fournisseur.setImageFile(imageFile);
	        Fournisseur createdFournisseur = fournisseurService.createFournisseur(fournisseur);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdFournisseur);
	    }
	    @PutMapping("/{dataId}")
	    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Long dataId, @RequestBody Fournisseur secteur) {
	        Fournisseur updatedSecteur = fournisseurService.updateFournisseur(dataId,secteur);
	        if (updatedSecteur != null) {
	            return ResponseEntity.ok(updatedSecteur);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
