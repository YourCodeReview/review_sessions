package com.is.model.Response;

public class Data {

	private String result;
	private String resultMessage;
	private String reportBase64;
	private String token;
	
	
	public Data() {
		super();
	}


	public Data(String result, String resultMessage, String reportBase64,
			String token) {
		super();
		this.result = result;
		this.resultMessage = resultMessage;
		this.reportBase64 = reportBase64;
		this.token = token;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getResultMessage() {
		return resultMessage;
	}


	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}


	public String getReportBase64() {
		return reportBase64;
	}


	public void setReportBase64(String reportBase64) {
		this.reportBase64 = reportBase64;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	


	
	
}
