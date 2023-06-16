package com.example.demo.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Machine;
import com.example.demo.entities.Piece;
import com.example.demo.service.GcsStorageService;
import com.example.demo.service.MachineService;
import com.example.demo.service.PieceService;

@RestController
@RequestMapping("/piece")
@CrossOrigin(origins = "http://localhost:4200")

public class PieceContoller {
    private static final String UPLOADS_DIR = "uploads";

    @Autowired
    private PieceService pieceService;
   
  
    @GetMapping
    public ResponseEntity<List<Piece>> getAllPieces() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Piece>> machinesFuture = pieceService.getAllPieces();
        List<Piece> machines = machinesFuture.get();
        return ResponseEntity.ok(machines);
    }



    @GetMapping("/count")
    public CompletableFuture<Integer> getPieceCount() {
        return pieceService.getAllPieces()
            .thenApply(machineList -> machineList.size());
    }

    @GetMapping("/{ref}")
    public ResponseEntity<Piece> getPieceByRef(@PathVariable String ref) {
        Piece machine = pieceService.getPieceByRef(ref);
        if (machine != null) {
            return ResponseEntity.ok(machine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
   /* @PostMapping
    public ResponseEntity<Piece> createPiece(@RequestBody Piece piece, @RequestParam("image") MultipartFile imageFile) {
        try {
            // Enregistrer la pièce avec l'URL de l'image mise à jour dans votre base de données
            Piece createdPiece = pieceService.createPiece(piece, imageFile);

            // Stocker l'image dans le système de stockage
            String imageUrl = storageService.storeImage(imageFile);
            createdPiece.setImageURL(imageUrl); // Mettre à jour l'URL de l'image dans l'objet créé

            return ResponseEntity.status(HttpStatus.CREATED).body(createdPiece);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    // Autres méthodes de votre contrôleur...
*/



    @PutMapping("/{ref}")
    public ResponseEntity<Piece> updatePiece(@PathVariable String ref, @RequestBody Piece machine) {
        Piece updatedMachine = pieceService.updatePiece(ref, machine);
        if (updatedMachine != null) {
            return ResponseEntity.ok(updatedMachine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{ref}")
    public ResponseEntity<Void> deletePiece(@PathVariable String ref) {
        boolean deleted = pieceService.deletePiece(ref);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
