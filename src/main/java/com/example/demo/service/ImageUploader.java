package com.example.demo.service;


	import com.google.cloud.storage.*;

	public class ImageUploader {
	    private static final String BUCKET_NAME = "samco-6993f.appspot.com";

	    public void uploadImageToStorage(String imageName, byte[] imageBytes) {
	        Storage storage = StorageOptions.getDefaultInstance().getService();
	        BlobId blobId = BlobId.of(BUCKET_NAME, imageName);
	        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
	        Blob blob = storage.create(blobInfo, imageBytes);
	        System.out.println("Image uploaded successfully to Google Cloud Storage.");
	    }
	}


