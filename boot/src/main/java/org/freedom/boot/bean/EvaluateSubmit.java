package org.freedom.boot.bean;

public class EvaluateSubmit {
	Evaluate evaluate;
	Integer ifHyperAdmin;
	
	public EvaluateSubmit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EvaluateSubmit(Evaluate evaluate, Integer ifHyperAdmin) {
		super();
		this.evaluate = evaluate;
		this.ifHyperAdmin = ifHyperAdmin;
	}
	public Evaluate getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(Evaluate evaluate) {
		this.evaluate = evaluate;
	}
	public Integer getIfHyperAdmin() {
		return ifHyperAdmin;
	}
	public void setIfHyperAdmin(Integer ifHyperAdmin) {
		this.ifHyperAdmin = ifHyperAdmin;
	}
	
}
