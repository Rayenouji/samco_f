package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Famille;
import com.example.demo.entities.Fournisseur;
import com.example.demo.entities.users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Service
public class FamilleService {
    private static final String COLLECTION_NAME = "famille";

    public Famille getFamilleByRef(String refFamille) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refFamille);

        CompletableFuture<Famille> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Famille secteur = dataSnapshot.getValue(Famille.class);
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

    public long getTotalItems() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

        CompletableFuture<Long> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long totalItems = dataSnapshot.getChildrenCount();
                future.complete(totalItems);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        };

        databaseRef.addListenerForSingleValueEvent(eventListener);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return 0;
        }
    }

    public Famille createFamille(Famille secteur) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(secteur.getRefFamille());

        secteurRef.setValue(secteur, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                // Success
            } else {
                // Error
            }
        });

        return secteur;
    }
    public CompletableFuture<List<Famille>> getAllFamilles() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

        CompletableFuture<List<Famille>> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Famille> secteurList = new ArrayList<>();
                for (DataSnapshot secteurSnapshot : dataSnapshot.getChildren()) {
                    Famille secteur = secteurSnapshot.getValue(Famille.class);
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

	



    public Famille updateFamille(String refFamille, Famille secteur) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refFamille);

        secteurRef.setValue(secteur, null);
        return secteur;
    }

    public boolean deleteFamille(String refFamille) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refFamille);

        secteurRef.removeValue(null);

        return true;
    }
    public CompletableFuture<List<Famille>> getAllFamilles(int startIndex, int pageSize) {
        CompletableFuture<Long> totalItemsFuture = CompletableFuture.supplyAsync(() -> getTotalItems());
        CompletableFuture<List<Famille>> familleListFuture = totalItemsFuture.thenCompose(totalItems -> {
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
            CompletableFuture<List<Famille>> future = new CompletableFuture<>();

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Famille> familleList = new ArrayList<>();
                    int count = 0;
                    for (DataSnapshot familleSnapshot : dataSnapshot.getChildren()) {
                        if (count >= startIndex && count < startIndex + pageSize) {
                            Famille famille = familleSnapshot.getValue(Famille.class);
                            familleList.add(famille);
                        }
                        count++;
                    }
                    future.complete(familleList);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    future.completeExceptionally(databaseError.toException());
                }
            };

            databaseRef.addListenerForSingleValueEvent(eventListener);

            return future;
        });

        return familleListFuture;
    }
}
