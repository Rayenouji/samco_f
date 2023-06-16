package com.example.demo.entities;

import jakarta.persistence.Id;

public class users {
	private String email;
	private String username;
	private String name;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String usertype;
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public users(String email, String name, String password, String usertype, String imageURL, String id,String username) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.usertype = usertype;
		this.imageURL = imageURL;
		this.id = id;
		this.username = username;
	}
	private String imageURL;
	@Id
	private String id;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public users() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "users [email=" + email + ", name=" + name + ", password=" + password + ", usertype=" + usertype
				+ ", id=" + id + "]";
	}
	

}
