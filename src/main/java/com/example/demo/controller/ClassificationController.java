package com.example.demo.controller;

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
import com.example.demo.entities.Machine;
import com.example.demo.entities.Secteur;
import com.example.demo.service.ClassificationService;
import com.example.demo.service.SecteurService;

@RestController
@RequestMapping("/classification")
@CrossOrigin(origins = "http://localhost:4200")
public class ClassificationController {
	 @Autowired
	    private ClassificationService classificationService;
	 @GetMapping("/{refClassification}")
	    public ResponseEntity<Classification> getClassificationByRef(@PathVariable String refClassification) {
	        Classification secteur = classificationService.getClassificationByRef(refClassification);
	        if (secteur != null) {
	            return ResponseEntity.ok(secteur);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	   @GetMapping
	    public ResponseEntity<List<Classification>> getAllClassificationss() throws ExecutionException, InterruptedException {
	        CompletableFuture<List<Classification>> secteursFuture = classificationService.getAllClassifications();
	        List<Classification> secteurs = secteursFuture.get();
	        return ResponseEntity.ok(secteurs);
	    }
	   @DeleteMapping("/{refClassification}")
	    public ResponseEntity<Void> deleteClassification(@PathVariable String refClassification) {
	        boolean deleted = classificationService.deleteclassification(refClassification);
	        if (deleted) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	   @PostMapping
	    public ResponseEntity<Classification> createclassification(@RequestBody Classification machine) {
	        // Mettre à jour la valeur de ref avec la clé souhaitée
	    //    machine.setRef("votre_clé_primaire");

	        Classification createdMachine = classificationService.createClassification(machine);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdMachine);
	    }
	   @GetMapping("/count")
	    public CompletableFuture<Integer> getMachineCount() {
	        return classificationService.getAllClassifications()
	            .thenApply(machineList -> machineList.size());
	    }



	    @PutMapping("/{refClassification}")
	    public ResponseEntity<Classification> updateClassification(@PathVariable String refClassification, @RequestBody Classification secteur) {
	        Classification updatedSecteur = classificationService.updateSecteur(refClassification,secteur);
	        if (updatedSecteur != null) {
	            return ResponseEntity.ok(updatedSecteur);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
