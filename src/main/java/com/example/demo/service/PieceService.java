package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Machine;
import com.example.demo.entities.Piece;
import com.example.demo.entities.Secteur;
import com.google.auth.oauth2.GoogleCredentials;
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
public class PieceService {

    private static final String COLLECTION_NAME = "piece";
    public Piece getPieceByRef(String ref) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(ref);

        CompletableFuture<Piece> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Piece secteur = dataSnapshot.getValue(Piece.class);
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
 

    public Piece createPiece(Piece piece, MultipartFile imageFile) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference pieceRef = databaseRef.push(); // Génère une nouvelle clé pour l'élément

        pieceRef.setValue(piece, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                String pieceId = databaseReference.getKey();
                String imagePath = "pieces/" + pieceId + ".jpg"; // Chemin de stockage de l'image dans Firebase Storage

                // Enregistrer l'image dans Firebase Storage
                uploadImageToStorage(imageFile, imagePath, pieceRef.child("imageURL"));
            } else {
                // Une erreur s'est produite lors de l'enregistrement de la pièce
            }
        });

        return piece;
    }
    public void uploadImageToStorage(MultipartFile imageFile, String imagePath, DatabaseReference imageURLRef) {
        try {
            // Get Firebase Storage credentials
            GoogleCredentials credentials = GoogleCredentials.fromStream(getClass().getResourceAsStream("/path/to/serviceAccountKey.json"));
            
            // Initialize Firebase Storage
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            
            // Create a full image path in Firebase Storage
            String bucketName = "gs://samco-6993f.appspot.com"; // Replace with your Firebase Storage bucket name
            String fullImagePath = bucketName + "/" + imagePath;
            
            // Upload the image to Firebase Storage
            BlobId blobId = BlobId.of(bucketName, imagePath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(imageFile.getContentType()).build();
            Blob blob = storage.create(blobInfo, imageFile.getInputStream());
            
            // Update the image URL in the database
            String downloadURL = blob.getMediaLink();
            imageURLRef.setValue(downloadURL, null);
        } catch (IOException e) {
            // Handle errors related to image upload
        }
    }
  /*  private void uploadImageToStorage(MultipartFile imageFile, String imagePath, DatabaseReference imageURLRef) {
        // Convert the MultipartFile to byte array
        byte[] imageData;
        try {
            imageData = imageFile.getBytes();
        } catch (IOException e) {
            // Handle the exception if the conversion fails
            return;
        }

        // Create an instance of Storage using the default options
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Specify the bucket name and the image file path
        String bucketName = "samco-6993f.appspot.com";
        BlobId blobId = BlobId.of(bucketName, imagePath);

        // Create a Blob from the BlobId and image data
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
        Blob blob = storage.create(blobInfo, imageData);

        // Get the image URL from the Blob
        String imageURL = blob.getMediaLink();

        // Update the imageURL in the Realtime Database
        imageURLRef.setValue(imageURL, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                // An error occurred while updating the imageURL
            }
        });
    }
*/
    private String getDownloadUrl(String imagePath) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of("samco-6993f.appspot.com", imagePath);
        Blob blob = storage.get(blobId);
        return blob.getMediaLink();
    }

    public Piece updatePiece(String ref, Piece machine) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference machineRef = databaseRef.child(ref);

        machineRef.setValue(machine, null);
        return machine;
    }

    public boolean deletePiece(String ref) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference machineRef = databaseRef.child(ref);

        machineRef.removeValue(null);
        return true;
    }
 public CompletableFuture<List<Piece>> getAllPieces() {
    	
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

        CompletableFuture<List<Piece>> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Piece> machineList = new ArrayList<>();
                for (DataSnapshot machineSnapshot : dataSnapshot.getChildren()) {
                    Piece machine = machineSnapshot.getValue(Piece.class);
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
    }
   

    private String getImageUrlFromStorage(String imageFileName) {
        // Créer une instance de Storage à partir des options par défaut
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Spécifier le nom du bucket et le nom de l'objet/image
        String bucketName = "samco-6993f.appspot.com";
        String objectName = "Android Images/" + imageFileName;

        // Créer un BlobId à partir du bucket et du nom de l'objet
        BlobId blobId = BlobId.of(bucketName, objectName);

        // Récupérer l'URL de l'objet/image à partir du BlobId
        Blob blob = storage.get(blobId);
        String imageUrl = blob.getMediaLink();

        return imageUrl;
    }

}


