package com.is.model.FirstRequest;

public class Security {
	private String pLogin;
	private String pPassword;
	
	
	public Security() {
		super();
	}


	public Security(String pLogin, String pPassword) {
		super();
		this.pLogin = pLogin;
		this.pPassword = pPassword;
	}


	public String getpLogin() {
		return pLogin;
	}


	public void setpLogin(String pLogin) {
		this.pLogin = pLogin;
	}


	public String getpPassword() {
		return pPassword;
	}


	public void setpPassword(String pPassword) {
		this.pPassword = pPassword;
	}

	

}
