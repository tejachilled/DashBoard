package com.peer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.hibernate.validator.constraints.NotBlank;

public class BeanClass {
	@NotBlank(message="username cannot be blank")
	private String username;
	@NotBlank(message="password cannot be blank")
	private String password;
	private int count;
	private String role;
	private int imagesNumber;
	private String Imagefile;
	private String content;
	private String zipFile;
	private String link;
	private String assignment_name;
	private String group_id;
	private String assignment_id;
	private String submission_date;
	private int charCount;
	private int wordCount;
	private String fullName;
	private String fullContext;
	private ArrayList<String> images;
	private ArrayList<String> groups;
	private double totMarks;
	private double average;
	private ArrayList<OperationParameters> toDisplay;
	private HashMap<String, BeanClass> peerList;
	private ArrayList<OperationParameters> reviewCriteria;
	private ArrayList<OperationParameters> teacherMarks;
	private ArrayList<Integer> studentTotMarks;
	private int teacherTotMarks;
	private int randomNumber;
	
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

	public ArrayList<String> getImages() {
		return images;
	}

	public void setImages(ArrayList<String> images) {
		this.images = images;
	}

	public String getFullContext() {
		return fullContext;
	}

	public void setFullContext(String fullContext) {
		this.fullContext = fullContext;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ArrayList<String> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<String> groups) {
		this.groups = groups;
	}

	public String getAssignment_id() {
		return assignment_id;
	}

	public void setAssignment_id(String assignment_id) {
		this.assignment_id = assignment_id;
	}

	public HashMap<String, BeanClass> getPeerList() {
		return peerList;
	}

	public void setPeerList(HashMap<String, BeanClass> peerList) {
		this.peerList = peerList;
	}

	public ArrayList<OperationParameters> getReviewCriteria() {
		return reviewCriteria;
	}

	public void setReviewCriteria(ArrayList<OperationParameters> reviewCriteria) {
		this.reviewCriteria = reviewCriteria;
	}

	public ArrayList<OperationParameters> getToDisplay() {
		return toDisplay;
	}

	public void setToDisplay(ArrayList<OperationParameters> toDisplay) {
		this.toDisplay = toDisplay;
	}

	public ArrayList<OperationParameters> getTeacherMarks() {
		return teacherMarks;
	}

	public void setTeacherMarks(ArrayList<OperationParameters> teacherMarks) {
		this.teacherMarks = teacherMarks;
	}

	public ArrayList<Integer> getStudentTotMarks() {
		return studentTotMarks;
	}

	public void setStudentTotMarks(ArrayList<Integer> studentTotMarks) {
		this.studentTotMarks = studentTotMarks;
	}

	public int getTeacherTotMarks() {
		return teacherTotMarks;
	}

	public void setTeacherTotMarks(int teacherTotMarks) {
		this.teacherTotMarks = teacherTotMarks;
	}

	public double getTotMarks() {
		return totMarks;
	}

	public void setTotMarks(double totMarks) {
		this.totMarks = totMarks;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public int getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(int randomNumber) {
		this.randomNumber = randomNumber;
	}


}
