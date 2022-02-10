package com.is.model.Response;

public class Kresponce {
	
	private Data data;
	private String errorMessage;
	private Integer code;
	
	
	public Kresponce() {
		super();
	}


	public Kresponce(Data data, String errorMessage, Integer code) {
		super();
		this.data = data;
		this.errorMessage = errorMessage;
		this.code = code;
	}


	public Data getData() {
		return data;
	}


	public void setData(Data data) {
		this.data = data;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public Integer getCode() {
		return code;
	}


	public void setCode(Integer code) {
		this.code = code;
	}

	
	
}
