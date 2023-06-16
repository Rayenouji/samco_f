package com.example.demo.service;


import com.example.demo.entities.Secteur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class SecteurService {

    private static final String COLLECTION_NAME = "secteur";

    public Secteur getSecteurByRef(String refSecteur) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refSecteur);

        CompletableFuture<Secteur> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Secteur secteur = dataSnapshot.getValue(Secteur.class);
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

    public Secteur createSecteur(Secteur secteur) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(secteur.getRefSecteur());

        secteurRef.setValue(secteur, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                // Success
            } else {
                // Error
            }
        });

        return secteur;
    }

    public Secteur updateSecteur(String refSecteur, Secteur secteur) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refSecteur);

        secteurRef.setValue(secteur, null);
        return secteur;
    }

    public boolean deleteSecteur(String refSecteur) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refSecteur);

        secteurRef.removeValue(null);
        return true;
    }

    public CompletableFuture<List<Secteur>> getAllSecteurs() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

        CompletableFuture<List<Secteur>> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Secteur> secteurList = new ArrayList<>();
                for (DataSnapshot secteurSnapshot : dataSnapshot.getChildren()) {
                    Secteur secteur = secteurSnapshot.getValue(Secteur.class);
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
