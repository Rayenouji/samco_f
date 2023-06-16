package com.example.demo.entities;

public class Classification {
	private String classification;
	private String refClassification;
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getRefClassification() {
		return refClassification;
	}
	public void setRefClassification(String refClassification) {
		this.refClassification = refClassification;
	}
	public Classification(String classification, String refClassification) {
		super();
		this.classification = classification;
		this.refClassification = refClassification;
	}
	public Classification() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
