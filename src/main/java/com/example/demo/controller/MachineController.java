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

import com.example.demo.entities.Classification;
import com.example.demo.entities.Famille;
import com.example.demo.entities.Intervention;
import com.example.demo.entities.Machine;
import com.example.demo.entities.users;
import com.example.demo.service.MachineService;

@RestController
@RequestMapping("/machine")
@CrossOrigin(origins = "http://localhost:4200")

public class MachineController {

    @Autowired
    private MachineService machineService;
    
  /*
    @GetMapping
    public ResponseEntity<List<Machine>> getAllMachines() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Machine>> machinesFuture = machineService.getAllMachines();
        List<Machine> machines = machinesFuture.get();
        
        return ResponseEntity.ok(machines);
    }*/
    @GetMapping
    public ResponseEntity<List<Machine>> getAllMachines() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Machine>> machinesFuture = machineService.getAllMachines();
        List<Machine> machines = machinesFuture.get();
        return ResponseEntity.ok(machines);
    }

   /* 
    @GetMapping("/{ref}")
    public ResponseEntity<Machine> getMachineByRefpage(@PathVariable String ref) {
        Machine machine = machineService.getMachineByRef(ref);
        if (machine != null) {
            return ResponseEntity.ok(machine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

   
    @GetMapping("/count")
    public CompletableFuture<Integer> getMachineCount() {
        return machineService.getAllMachines()
            .thenApply(machineList -> machineList.size());
    }
   @GetMapping("/page/{page}")
	  public ResponseEntity<List<Machine>> getMachineByPage(@PathVariable int page) throws ExecutionException, InterruptedException {
	      int pageSize = 5; // Nombre d'utilisateurs à afficher par page
	      int startIndex = (page - 1) * pageSize; // Indice de départ pour la pagination
	      CompletableFuture<List<Machine>> usersFuture = machineService.getAllMachines();
	      List<Machine> allUsers = usersFuture.get();

	      List<Machine> usersPage = new ArrayList<>();
	      for (int i = startIndex; i < Math.min(startIndex + pageSize, allUsers.size()); i++) {
	          usersPage.add(allUsers.get(i));
	      }

	      return ResponseEntity.ok(usersPage);
	  }


    @GetMapping("/search")
    public ResponseEntity<Machine> searchMachineByRef(@RequestParam("ref") String ref) {
        Machine machine = machineService.searchMachineByRef(ref);

        if (machine != null) {
            return ResponseEntity.ok(machine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Machine> createMachine(@RequestBody Machine machine) {
        // Mettre à jour la valeur de ref avec la clé souhaitée
    //    machine.setRef("votre_clé_primaire");

        Machine createdMachine = machineService.createMachine(machine);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMachine);
    }
  /*  @GetMapping("/search")
    public ResponseEntity<Machine> searchMachineByRef(@RequestParam("ref") String ref) {
        // Effectuez la recherche de la machine par référence dans votre base de données en temps réel
        Machine machine = machineService.searchByRef(ref);

        if (machine != null) {
            return ResponseEntity.ok(machine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
*/
    @GetMapping("/{ref}")
    public ResponseEntity<Machine> getMachineByRef(@PathVariable String ref) {
        Machine secteur = machineService.getMachineByRef(ref);
        if (secteur != null) {
            return ResponseEntity.ok(secteur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{ref}")
    public ResponseEntity<Machine> updateMachine(@PathVariable String ref, @RequestBody Machine machine) {
        Machine updatedMachine = machineService.updateMachine(ref, machine);
        if (updatedMachine != null) {
            return ResponseEntity.ok(updatedMachine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{ref}")
    public ResponseEntity<Void> deleteMachine(@PathVariable String ref) {
        boolean deleted = machineService.deleteMachine(ref);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}