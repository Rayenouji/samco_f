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

import com.example.demo.entities.Intervention;
import com.example.demo.entities.Secteur;
import com.example.demo.entities.Types;
import com.example.demo.service.InterventionService;
import com.example.demo.service.TypeService;

@RestController
@RequestMapping("/Types")
@CrossOrigin(origins = "http://localhost:4200")
public class TypeController {
	@Autowired
    private TypeService typeService;

    @GetMapping
    public ResponseEntity<List<Types>> getAllTypes() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Types>> secteursFuture = typeService.getAllTypess();
        List<Types> secteurs = secteursFuture.get();
        return ResponseEntity.ok(secteurs);
    }
    @PostMapping
    public ResponseEntity<Types> createType(@RequestBody Types secteur) {
        Types createdSecteur = typeService.createType(secteur);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSecteur);
    }
    @PutMapping("/{refType}")
    public ResponseEntity<Types> updateType(@PathVariable String refSecteur, @RequestBody Types secteur) {
        Types updatedSecteur = typeService.updateType(refSecteur, secteur);
        if (updatedSecteur != null) {
            return ResponseEntity.ok(updatedSecteur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/count")
    public CompletableFuture<Integer> getMachineCount() {
        return typeService.getAllTypess()
            .thenApply(machineList -> machineList.size());
    }

    @DeleteMapping("/{refType}")
    public ResponseEntity<Void> deleteType(@PathVariable String refSecteur) {
        boolean deleted = typeService.deleteType(refSecteur);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

 
}
