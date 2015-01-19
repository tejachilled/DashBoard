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
	private int count;
	private int imagesNumber;
	private String Imagefile;
	private String Content;
	private String zipFile;
	private String link;
	private String assignment_name;
	private String group_id;
	private String submission_date;
	private int charCount;
	private int wordCount;
	private String fullName;	
	
	public String getAssignment_name() {
		return assignment_name;
	}

	public void setAssignment_name(String assignment_name) {
		this.assignment_name = assignment_name;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getZipFile() {
		return zipFile;
	}

	public void setZipFile(String zipFile) {
		this.zipFile = zipFile;
	}

	private ArrayList<String> review;
	
	public ArrayList<String> getReview() {
		return review;
	}

	public void setReview(ArrayList<String> review) {
		this.review = review;
	}



	public String getImagefile() {
		return Imagefile;
	}

	public void setImagefile(String string) {
		Imagefile = string;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
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

	public String getSubmission_date() {
		return submission_date;
	}

	public void setSubmission_date(String submission_date) {
		this.submission_date = submission_date;
	}

	public int getImagesNumber() {
		return imagesNumber;
	}

	public void setImagesNumber(int imagesNumber) {
		this.imagesNumber = imagesNumber;
	}

	public int getCharCount() {
		return charCount;
	}

	public void setCharCount(int length) {
		this.charCount = length;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	

	

}
