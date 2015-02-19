package com.peer.controller;

import java.util.ArrayList;
import java.util.HashMap;


public class BeanTeacher {

	private String username;
	private HashMap<String, BeanClass> studentList;
	private int randomNumber;
	private String groupid;
	private int assignment_id;
	private String assignment_name;
	private String submissionCount;
	
	public int getRandomNumber() {
		return randomNumber;
	}
	public void setRandomNumber(int randomNumber) {
		this.randomNumber = randomNumber;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public int getAssignment_id() {
		return assignment_id;
	}
	public void setAssignment_id(int assignment_id) {
		this.assignment_id = assignment_id;
	}
	public String getAssignment_name() {
		return assignment_name;
	}
	public void setAssignment_name(String assignment_name) {
		this.assignment_name = assignment_name;
	}
	public String getSubmissionCount() {
		return submissionCount;
	}
	public void setSubmissionCount(String submissionCount) {
		this.submissionCount = submissionCount;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public HashMap<String, BeanClass> getStudentList() {
		return studentList;
	}
	public void setStudentList(HashMap<String, BeanClass> studentList) {
		this.studentList = studentList;
	}
	
}
