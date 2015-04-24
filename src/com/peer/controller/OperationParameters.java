package com.peer.controller;

public class OperationParameters {

	private String criteria;
	private String weight;
	private boolean teacher_evaluation;
	
	public OperationParameters(String criteria,String weight){
		this.setCriteria(criteria);
		this.setWeight(weight);
	}
	public OperationParameters(){
	}
	public String getCriteria() {
		return criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public boolean isTeacher_evaluation() {
		return teacher_evaluation;
	}
	public void setTeacher_evaluation(boolean teacher_evaluation) {
		this.teacher_evaluation = teacher_evaluation;
	}
}
