package com.example.demo.service;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FirebaseStorageService {
	  private static final String BUCKET_NAME = "samco-6993f.appspot.com"; // Remplacez par le nom de votre bucket Firebase Storage

	    public String uploadImage(MultipartFile image) throws IOException {
	        String fileName = generateUniqueFileName(image.getOriginalFilename());

	        // Initialisez la connexion à Firebase Storage
	        Storage storage = StorageOptions.getDefaultInstance().getService();

	        // Définissez l'emplacement du fichier sur Firebase Storage
	        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
	        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

	        // Téléchargez le fichier sur Firebase Storage
	        Blob blob = storage.create(blobInfo, image.getBytes());

	        // Récupérez l'URL du fichier téléchargé
	        String imageURL = blob.getMediaLink();

	        return imageURL;
	    }

	    private String generateUniqueFileName(String originalFileName) {
	        // Générez un nom de fichier unique à l'aide d'un UUID
	        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
	        return UUID.randomUUID().toString() + extension;
	    }
	}