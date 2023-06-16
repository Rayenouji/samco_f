package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Classification;
import com.example.demo.entities.users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
@Service
public class usersService {

   private static final String COLLECTION_NAME = "users";

   public users getUserById(String id) {
       DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
       DatabaseReference secteurRef = databaseRef.child(id);

       CompletableFuture<users> future = new CompletableFuture<>();

       ValueEventListener eventListener = new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               users secteur = dataSnapshot.getValue(users.class);
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

   public users createUser(users secteur) {
       DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
       DatabaseReference secteurRef = databaseRef.child(secteur.getId());

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

   public boolean deleteUser(String name) {
       DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
       DatabaseReference secteurRef = databaseRef.child(name);

       secteurRef.removeValue(null);
       return true;
   }

   public CompletableFuture<List<users>> getAllUsers() {
       DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

       CompletableFuture<List<users>> future = new CompletableFuture<>();

       ValueEventListener eventListener = new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               List<users> secteurList = new ArrayList<>();
               for (DataSnapshot secteurSnapshot : dataSnapshot.getChildren()) {
                   users secteur = secteurSnapshot.getValue(users.class);
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
