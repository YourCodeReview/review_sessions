package com.is.model.SecondRequest;

public class KatmReqS {
	private Data data;

	/**
	* No args constructor for use in serialization
	* 
	*/
	public KatmReqS() {
	}

	/**
	* 
	* @param data
	*/
	public KatmReqS(Data data) {
	super();
	
	this.data = data;
	}

	public Data getData() {
	return data;
	}

	public void setData(Data data) {
	this.data = data;
	}

}
