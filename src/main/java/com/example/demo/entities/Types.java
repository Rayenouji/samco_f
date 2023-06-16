package com.example.demo.entities;

public class Types {

    private String refType;
    private String classe;
    private String type;
	public Types() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Types(String refType, String classe, String type) {
		super();
		this.refType = refType;
		this.classe = classe;
		this.type = type;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
