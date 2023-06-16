package com.example.demo.entities;

import org.springframework.web.multipart.MultipartFile;

public class Fournisseur {
	private Long dataId;
	private String dataContact;
	private String dataLocal;
	private String dataNom;
	private String imageURL;
	 private MultipartFile imageFile;

	    public MultipartFile getImageFile() {
	        return imageFile;
	    }

	    public void setImageFile(MultipartFile imageFile) {
	        this.imageFile = imageFile;
	    }
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	public String getDataContact() {
		return dataContact;
	}
	public void setDataContact(String dataContact) {
		this.dataContact = dataContact;
	}
	public String getDataLocal() {
		return dataLocal;
	}
	public void setDataLocal(String dataLocal) {
		this.dataLocal = dataLocal;
	}
	public String getDataNom() {
		return dataNom;
	}
	public void setDataNom(String dataNom) {
		this.dataNom = dataNom;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public Fournisseur() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Fournisseur(Long dataId, String dataContact, String dataLocal, String dataNom, String imageUrl) {
		super();
		this.dataId = dataId;
		this.dataContact = dataContact;
		this.dataLocal = dataLocal;
		this.dataNom = dataNom;
		this.imageURL = imageUrl;
	}
	

}
