package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;

public class Machine {
	
	@Id
	private String ref;
	private String des;
	private String etat ;
	private String remarque;
	private String secteur;
	private String type;
	private Double prix;
	private String modele;
	private String fabricant;

    public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
	public String getFabricant() {
		return fabricant;
	}
	public void setFabricant(String fabricant) {
		this.fabricant = fabricant;
	}
	private String imageURL;

  	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getRemarque() {
		return remarque;
	}
	public void setRemarque(String remarque) {
		this.remarque = remarque;
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
	public Machine() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Machine(String des, String etat, String ref,String remarque,
			String secteur, String type,Double prix,String imageURL,String modele,String fabricant) {
		super();
		this.des = des;
		this.etat = etat;
		this.ref = ref;
		this.remarque = remarque;
		this.secteur = secteur;
		this.type = type;
		this.prix = prix;
		this.imageURL = imageURL;
		this.modele=modele;
		this.fabricant = fabricant;
	}
	public Double getPrix() {
		return prix;
	}
	public void setPrix(Double prix) {
		this.prix = prix;
	}
}
