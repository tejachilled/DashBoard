package com.peer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;


public class BeanTeacher {

	private String username;
	private HashMap<String, BeanClass> studentList;
	private int randomNumber;
	private String groupid;
	private int assignment_id;
	private int submissionCount;
	private ArrayList<String> groups;
	private ArrayList<String> assignids;
	private ArrayList<BeanClass> allStudents;
	private List<OperationParameters> operationParameterses = LazyList.decorate(new ArrayList<OperationParameters>(),FactoryUtils.instantiateFactory(OperationParameters.class));
	public List<OperationParameters> getOperationParameterses() {
		return operationParameterses;
	}
	public void setOperationParameterses(List<OperationParameters> operationParameterses) {
		this.operationParameterses = operationParameterses;
	}
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
	public int getSubmissionCount() {
		return submissionCount;
	}
	public void setSubmissionCount(int submissionCount) {
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
	
	public int getAssignment_id() {
		return assignment_id;
	}
	public void setAssignment_id(int assignment_id) {
		this.assignment_id = assignment_id;
	}
	public ArrayList<String> getGroups() {
		return groups;
	}
	public void setGroups(ArrayList<String> groups) {
		this.groups = groups;
	}
	public ArrayList<BeanClass> getAllStudents() {
		return allStudents;
	}
	public void setAllStudents(ArrayList<BeanClass> allStudents) {
		this.allStudents = allStudents;
	}

	public ArrayList<String> getAssignids() {
		return assignids;
	}
	public void setAssignids(ArrayList<String> assignids) {
		this.assignids = assignids;
	}

}
