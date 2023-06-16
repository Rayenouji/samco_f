package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Fournisseur;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
@Service
public class FournisseurService {
	 private static final String COLLECTION_NAME = "fournisseur";
	    private final String bucketName = "gs://samco-6993f.appspot.com";
	    private final Storage storage = StorageOptions.getDefaultInstance().getService();

	    public Fournisseur getFournisseurById(Long dataId) {
	        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
	        DatabaseReference secteurRef = databaseRef.child(dataId.toString());

	        CompletableFuture<Fournisseur> future = new CompletableFuture<>();

	        ValueEventListener eventListener = new ValueEventListener() {
	            @Override
	            public void onDataChange(DataSnapshot dataSnapshot) {
	                Fournisseur secteur = dataSnapshot.getValue(Fournisseur.class);
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

	    public Fournisseur createFournisseur(Fournisseur fournisseur) {
	        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
	        DatabaseReference secteurRef = databaseRef.child(String.valueOf(fournisseur.getDataId()));

	        // Vérifier si une image a été fournie
	        if (fournisseur.getImageFile() != null && !fournisseur.getImageFile().isEmpty()) {
	            try {
	                // Générer un nom de fichier unique
	                String fileName = UUID.randomUUID().toString();

	                // Récupérer l'extension du fichier
	                String fileExtension = FilenameUtils.getExtension(fournisseur.getImageFile().getOriginalFilename());

	                // Créer un objet Blob dans Firebase Storage
	                BlobId blobId = BlobId.of(bucketName, fileName + "." + fileExtension);
	                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(fournisseur.getImageFile().getContentType()).build();

	                // Upload du fichier dans Firebase Storage
	                Blob blob = storage.create(blobInfo, fournisseur.getImageFile().getBytes());

	                // Récupérer l'URL de téléchargement de l'image
	                String imageURL = blob.getMediaLink();

	                // Mettre à jour l'URL de l'image dans l'objet Fournisseur
	                fournisseur.setImageURL(imageURL);
	            } catch (IOException e) {
	                e.printStackTrace();
	                // Gérer l'erreur lors de l'upload de l'image
	            }
	        }

	        secteurRef.setValue(fournisseur, (databaseError, databaseReference) -> {
	            if (databaseError == null) {
	                // Succès
	            } else {
	                // Erreur
	            }
	        });

	        return fournisseur;
	    }

	    public Fournisseur updateFournisseur(Long dataId, Fournisseur secteur) {
	        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
	        DatabaseReference secteurRef = databaseRef.child(dataId.toString());

	        secteurRef.setValue(secteur, null);
	        return secteur;
	    }

	    public boolean deletefournisseur(Long dataId ) {
	        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
	        DatabaseReference secteurRef = databaseRef.child(dataId.toString());

	        secteurRef.removeValue(null);
	        return true;
	    }

	    public CompletableFuture<List<Fournisseur>> getAllFournisseurs() {
	        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

	        CompletableFuture<List<Fournisseur>> future = new CompletableFuture<>();

	        ValueEventListener eventListener = new ValueEventListener() {
	            @Override
	            public void onDataChange(DataSnapshot dataSnapshot) {
	                List<Fournisseur> secteurList = new ArrayList<>();
	                for (DataSnapshot secteurSnapshot : dataSnapshot.getChildren()) {
	                    Fournisseur secteur = secteurSnapshot.getValue(Fournisseur.class);
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


