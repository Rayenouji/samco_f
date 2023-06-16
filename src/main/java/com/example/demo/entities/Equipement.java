package com.example.demo.entities;

public class Equipement {
	private String ref;
    private String secteur;//secteur+refmachine
    private String type;
    private String des;
    private String etat;
    private String fournisseur;
    private Double prix;
    private String utilisation;
    private String imageURL;
	public Equipement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Equipement(String ref, String secteur, String type, String des, String etat, String fournisseur, double prix,
			String utilisation, String imageURL) {
		super();
		this.ref = ref;
		this.secteur = secteur;
		this.type = type;
		this.des = des;
		this.etat = etat;
		this.fournisseur = fournisseur;
		this.prix = prix;
		this.utilisation = utilisation;
		this.imageURL = imageURL;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getSecteur() {
		return secteur;
	}
	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public String getUtilisation() {
		return utilisation;
	}
	public void setUtilisation(String utilisation) {
		this.utilisation = utilisation;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

}
