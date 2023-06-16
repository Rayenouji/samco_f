package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Classification;
import com.example.demo.entities.Secteur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Service
public class ClassificationService {
    private static final String COLLECTION_NAME = "classification";

    public Classification getClassificationByRef(String refClassification) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refClassification);

        CompletableFuture<Classification> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Classification secteur = dataSnapshot.getValue(Classification.class);
                future.complete(secteur);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        };

        secteurRef.addListenerForSingleValueEvent(eventListener);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    public Classification createClassification(Classification secteur) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(secteur.getRefClassification());

        secteurRef.setValue(secteur, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                // Success
            } else {
                // Error
            }
        });

        return secteur;
    }

    public Classification updateSecteur(String refClassification, Classification secteur) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refClassification);

        secteurRef.setValue(secteur, null);
        return secteur;
    }

    public boolean deleteclassification(String refClassification) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refClassification);

        secteurRef.removeValue(null);
        return true;
    }

    public CompletableFuture<List<Classification>> getAllClassifications() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

        CompletableFuture<List<Classification>> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Classification> secteurList = new ArrayList<>();
                for (DataSnapshot secteurSnapshot : dataSnapshot.getChildren()) {
                    Classification secteur = secteurSnapshot.getValue(Classification.class);
                    secteurList.add(secteur);
                }
                future.complete(secteurList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        };

        databaseRef.addListenerForSingleValueEvent(eventListener);

        return future;
    }


}
