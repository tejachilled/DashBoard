package com.peer.controller;

public class BeanMarks {

	private String userId;
	private String peerId;
	private int count;
	private int analysis;
	private int design;
	private int vc;
	private int orginality;
	private int aesthetic;
	private int consistency;
	private int total;
	private boolean teacher_evaluation;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPeerId() {
		return peerId;
	}
	public void setPeerId(String peerId) {
		this.peerId = peerId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getAnalysis() {
		return analysis;
	}
	public void setAnalysis(int analysis) {
		this.analysis = analysis;
	}
	public int getDesign() {
		return design;
	}
	public void setDesign(int design) {
		this.design = design;
	}
	public int getVc() {
		return vc;
	}
	public void setVc(int vc) {
		this.vc = vc;
	}
	
	public int getAesthetic() {
		return aesthetic;
	}
	public void setAesthetic(int aesthetic) {
		this.aesthetic = aesthetic;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public boolean isTeacher_evaluation() {
		return teacher_evaluation;
	}
	public void setTeacher_evaluation(boolean teacher_evaluation) {
		this.teacher_evaluation = teacher_evaluation;
	}
	public int getConsistency() {
		return consistency;
	}
	public void setConsistency(int consistency) {
		this.consistency = consistency;
	}
	public int getOrginality() {
		return orginality;
	}
	public void setOrginality(int orginality) {
		this.orginality = orginality;
	}
	
	
}
