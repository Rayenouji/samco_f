package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Equipement;
import com.example.demo.entities.Famille;
import com.example.demo.entities.Intervention;
import com.example.demo.entities.Secteur;
import com.example.demo.entities.Types;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
@Service
public class TypeService {
    private static final String COLLECTION_NAME = "type";


	public CompletableFuture<List<Types>> getAllTypess() {
		// TODO Auto-generated method stub
		DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

        CompletableFuture<List<Types>> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Types> secteurList = new ArrayList<>();
                for (DataSnapshot secteurSnapshot : dataSnapshot.getChildren()) {
                	Types secteur = secteurSnapshot.getValue(Types.class);
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
    }    public boolean deleteType(String refType) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refType);

        secteurRef.removeValue(null);
        return true;
    }

    public Types createType(Types secteur) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(secteur.getRefType());

        secteurRef.setValue(secteur, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                // Success
            } else {
                // Error
            }
        });

        return secteur;
    }
    
    public Types updateType(String refType, Types secteur) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(refType);

        secteurRef.setValue(secteur, null);
        return secteur;
    }


}
