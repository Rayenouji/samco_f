package com.example.demo.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class ImageDownloader {
    private static final String BUCKET_NAME = "samco-6993f.appspot.com";
    private static final String OBJECT_NAME = "Android Images/image.jpg"; // Chemin de l'image dans le seau

    public static void main(String[] args) {
        // Créer une instance de Stockage Google Cloud
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Récupérer l'objet Blob
        BlobId blobId = BlobId.of(BUCKET_NAME, OBJECT_NAME);
        Blob blob = storage.get(blobId);

        // Télécharger l'image
        if (blob != null) {
            byte[] imageBytes = blob.getContent();
            // Faites quelque chose avec les données de l'image (par exemple, enregistrez-les dans un fichier)
            // ...
        } else {
            System.out.println("L'image n'a pas été trouvée.");
        }
    }
}
