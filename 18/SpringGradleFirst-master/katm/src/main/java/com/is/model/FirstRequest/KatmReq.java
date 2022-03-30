package com.is.model.FirstRequest;

public class KatmReq {
	
	private Security security;
	private Data data;
	
	
	public KatmReq() {
		super();
	}


	public KatmReq(Security security, Data data) {
		super();
		this.security = security;
		this.data = data;
	}


	public Security getSecurity() {
		return security;
	}


	public void setSecurity(Security security) {
		this.security = security;
	}


	public Data getData() {
		return data;
	}


	public void setData(Data data) {
		this.data = data;
	}
	
	

}
