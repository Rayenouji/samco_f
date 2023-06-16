package com.example.demo.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Equipement;
import com.example.demo.entities.Machine;
import com.example.demo.entities.Secteur;
import com.example.demo.service.EquipementService;
@RestController
@RequestMapping("/equipements")
@CrossOrigin(origins = "http://localhost:4200")

public class EquipementController {
	   @Autowired
	    private EquipementService machineService;
	    @GetMapping
	    public ResponseEntity<List<Equipement>> getAllMachines() throws ExecutionException, InterruptedException {
	        CompletableFuture<List<Equipement>> machinesFuture = machineService.getAllEquipements();
	        List<Equipement> machines = machinesFuture.get();
	        return ResponseEntity.ok(machines);
	    }
	    @PostMapping
	    public ResponseEntity<Equipement> createEquipement(@RequestBody Equipement equipement) {
	        Equipement createdEquipement = machineService.createEquipement(equipement);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdEquipement);
	    }

	    @GetMapping("/count")
	    public CompletableFuture<Integer> getEquipementCount() {
	        return machineService.getAllEquipements()
	            .thenApply(machineList -> machineList.size());
	    }
}
