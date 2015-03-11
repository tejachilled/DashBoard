package com.peer.controller;

public class OperationParameters {

	private String criteria;
	private String weight;
	
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
}
