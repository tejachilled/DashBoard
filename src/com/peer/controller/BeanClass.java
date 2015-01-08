package com.peer.controller;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class BeanClass {
	@NotBlank(message="username cannot be blank")
	private String username;
	@NotBlank(message="password cannot be blank")
	private String password;
	
	private String Imagefile;
	private String ContentFile;
	private ArrayList<String> review;
	
	public ArrayList<String> getReview() {
		return review;
	}

	public void setReview(ArrayList<String> review) {
		this.review = review;
	}

	public String getContentFile() {
		return ContentFile;
	}

	public void setContentFile(String contentFile) {
		ContentFile = contentFile;
	}

	public String getImagefile() {
		return Imagefile;
	}

	public void setImagefile(String imagefile) {
		Imagefile = imagefile;
	}

	BeanClass(){
		Database db = new Database();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
