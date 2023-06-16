package com.example.demo.entities;

public class Famille {
	private String famille;
	private String refFamille;
	public String getFamille() {
		return famille;
	}
	public void setFamille(String famille) {
		this.famille = famille;
	}
	public String getRefFamille() {
		return refFamille;
	}
	public void setRefFamille(String refFamille) {
		this.refFamille = refFamille;
	}
	public Famille(String famille, String refFamille) {
		super();
		this.famille = famille;
		this.refFamille = refFamille;
	}
	public Famille() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
