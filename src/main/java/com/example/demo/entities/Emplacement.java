package com.example.demo.entities;

public class Emplacement {
	private String emplacement;
	private String magasin;
	private String refEmplacement;
	public String getEmplacement() {
		return emplacement;
	}
	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}
	public String getMagasin() {
		return magasin;
	}
	public void setMagasin(String magasin) {
		this.magasin = magasin;
	}
	public String getRefEmplacement() {
		return refEmplacement;
	}
	public void setRefEmplacement(String refEmplacement) {
		this.refEmplacement = refEmplacement;
	}
	public Emplacement(String emplacement, String magasin, String refEmplacement) {
		super();
		this.emplacement = emplacement;
		this.magasin = magasin;
		this.refEmplacement = refEmplacement;
	}
	public Emplacement() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
