package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Equipement;
import com.example.demo.entities.Machine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
@Service
public class EquipementService {

    private static final String COLLECTION_NAME = "equipement";

    public Equipement getEquipementByRef(String ref) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference machineRef = databaseRef.child(ref);

         Equipement equipement = null;
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Equipement equipement = dataSnapshot.getValue(Equipement.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Gérer l'erreur
            }
        };

        machineRef.addListenerForSingleValueEvent(eventListener);
        return equipement;
    }

    public Equipement createEquipement(Equipement machine) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference machineRef = databaseRef.child(machine.getRef()); // Utilisez machine.getRef() comme clé

        machineRef.setValue(machine, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    // Success, handle the result if needed
                } else {
                    // Error occurred, handle the error
                }
            }
        });

        return machine;
    }

    public CompletableFuture<List<Equipement>> getAllEquipements() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

        CompletableFuture<List<Equipement>> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Equipement> machineList = new ArrayList<>();
                for (DataSnapshot machineSnapshot : dataSnapshot.getChildren()) {
                    Equipement machine = machineSnapshot.getValue(Equipement.class);
                    machineList.add(machine);
                }
                future.complete(machineList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        };

        databaseRef.addListenerForSingleValueEvent(eventListener);

        return future;

  }}

