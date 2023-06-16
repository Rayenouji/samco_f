package com.example.demo.service;
import com.example.demo.entities.Machine;
import com.example.demo.entities.Piece;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.StorageException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
@Service
public class MachineService {

    private static final String COLLECTION_NAME = "machine";

    public Machine getMachineByRef(String ref) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference secteurRef = databaseRef.child(ref);

        CompletableFuture<Machine> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Machine secteur = dataSnapshot.getValue(Machine.class);
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


/*
    public Machine createMachine(Machine machine) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference machineRef = databaseRef.child(machine.getRef()); // Utilisez machine.getRef() comme clé
        String imagePath =uploadImageToStorage(machine.getImageURL());

        machine.setImageURL(imagePath);
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
*/
    public Machine createMachine(Machine machine) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference machineRef = databaseRef.child(machine.getRef()); // Use machine.getRef() as the key
        byte[] imageBytes = getImageBytesFromURL(machine.getImageURL());
        String uniqueFileName = generateUniqueFileName();
        BlobId blobId = BlobId.of("samco-6993f.appspot.com", uniqueFileName);
        uploadImageToStorage(blobId, imageBytes);

        String imagePath = generateImagePath(uniqueFileName);
        machine.setImageURL(imagePath);

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

    private void uploadImageToStorage(BlobId blobId, byte[] imageBytes) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, imageBytes);
    }

    private byte[] getImageBytesFromURL(String imageURL) {
        try {
            URL url = new URL(imageURL);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        } catch (IOException e) {
            // Handle the exception if any error occurs during the image download
            e.printStackTrace();
        }

        return null; // Return null if image download fails
    }

    private String generateUniqueFileName() {
        // Generate a unique file name using UUID.randomUUID()
        String uniqueFileName = UUID.randomUUID().toString();
        return uniqueFileName;
    }

    private String generateImagePath(String fileName) {
        // Generate the image path based on the bucket name and file name
        String bucketName = "samco-6993f.appspot.com";
        String imagePath = "images/" + fileName;
        return "gs://" + bucketName + "/" + imagePath;
    }

    
    public Machine updateMachine(String ref, Machine machine) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference machineRef = databaseRef.child(ref);

        machineRef.setValue(machine, null);
        return machine;
    }

    public boolean deleteMachine(String ref) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference machineRef = databaseRef.child(ref);

        machineRef.removeValue(null);
        return true;
    }
    public CompletableFuture<List<Machine>> getAllMachines() {
    	
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);

        CompletableFuture<List<Machine>> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Machine> machineList = new ArrayList<>();
                for (DataSnapshot machineSnapshot : dataSnapshot.getChildren()) {
                    Machine machine = machineSnapshot.getValue(Machine.class);
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
   
    public Machine searchMachineByRef(String ref) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
        DatabaseReference machineRef = databaseRef.child(ref);

        Machine[] machine = new Machine[1]; // Utiliser un tableau pour stocker la machine

        CompletableFuture<Void> future = new CompletableFuture<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                machine[0] = dataSnapshot.getValue(Machine.class);
                future.complete(null); // Indiquer que la récupération est terminée
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException()); // Indiquer une erreur
            }
        };

        machineRef.addListenerForSingleValueEvent(eventListener);

        try {
            future.get(); // Attendre la fin de la récupération des données
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Gérer l'exception si nécessaire
        }

        return machine[0]; // Renvoyer la machine récupérée
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
