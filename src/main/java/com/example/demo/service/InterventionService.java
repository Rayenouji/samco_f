package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Intervention;
import com.example.demo.entities.Machine;
import com.example.demo.entities.Secteur;
import com.google.common.base.Optional;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Service
public class InterventionService {
    private static final String COLLECTION_NAME = "intervention";

   //Ajoutez d'autres méthodes pour les autres opérations CRUD, si nécessaire

	public CompletableFuture<List<Intervention>> getAllInterventions() {
		// TODO Auto-generated method stub
		DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

        CompletableFuture<List<Intervention>> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Intervention> secteurList = new ArrayList<>();
                for (DataSnapshot secteurSnapshot : dataSnapshot.getChildren()) {
                	Intervention secteur = secteurSnapshot.getValue(Intervention.class);
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
    }    public boolean deleteIntervention(String dataBon) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(dataBon);

        secteurRef.removeValue(null);
        return true;
    }
    public Intervention getinterventionByBon(Long dataBon) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(String.valueOf(dataBon)); // Convertir int en String

        CompletableFuture<Intervention> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intervention secteur = dataSnapshot.getValue(Intervention.class);
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


 
	// 	}
}