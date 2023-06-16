package com.example.demo.entities;

import org.springframework.web.multipart.MultipartFile;

public class Piece {
private String classification;
private String des;
private String emplacement;
private String famille;
private String fournisseur;
private Double prix;
private Long quantite;
private String ref;
private String refFour;
private MultipartFile imageFile;
private String imageURL;


public String getImageURL() {
	return imageURL;
}
public void setImageURL(String imageURL) {
	this.imageURL = imageURL;
}
public MultipartFile getImageFile() {
	return imageFile;
}
public void setImageFile(MultipartFile imageFile) {
	this.imageFile = imageFile;
}
public Piece(String classification, String des, String emplacement, String famille, String fournisseur, Double prix,
		Long quantite, String ref, String refFour, String imageURl) {
	super();
	this.classification = classification;
	this.des = des;
	this.emplacement = emplacement;
	this.famille = famille;
	this.fournisseur = fournisseur;
	this.prix = prix;
	this.quantite = quantite;
	this.ref = ref;
	this.refFour = refFour;
	this.imageURL = imageURl;
}
public Piece() {
	super();
	// TODO Auto-generated constructor stub
}
public String getClassification() {
	return classification;
}
public void setClassification(String classification) {
	this.classification = classification;
}
public String getDes() {
	return des;
}
public void setDes(String des) {
	this.des = des;
}
public String getEmplacement() {
	return emplacement;
}
public void setEmplacement(String emplacement) {
	this.emplacement = emplacement;
}
public String getFamille() {
	return famille;
}
public void setFamille(String famille) {
	this.famille = famille;
}
public String getFournisseur() {
	return fournisseur;
}
public void setFournisseur(String fournisseur) {
	this.fournisseur = fournisseur;
}
public Double getPrix() {
	return prix;
}
public void setPrix(Double prix) {
	this.prix = prix;
}
public Long getQuantite() {
	return quantite;
}
public void setQuantite(Long quantite) {
	this.quantite = quantite;
}
public String getRef() {
	return ref;
}
public void setRef(String ref) {
	this.ref = ref;
}
public String getRefFour() {
	return refFour;
}
public void setRefFour(String refFour) {
	this.refFour = refFour;
}
}
