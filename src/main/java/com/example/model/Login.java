package com.example.model;

public class Login {
	
    public enum LoginStatus {
        SIGNIN_SUCCESS,
        SIGNIN_FAILURE
    }

	private String ip;
	private String  date;
	private LoginStatus status;
	private String user;
	
	public Login(String ip, String date, LoginStatus status, String user) {
		super();
		this.ip = ip;
		this.date = date;
		this.status = status;
		this.user = user;
	}

	public Login() {
		this.ip = null;
		this.date = null;
		this.status = null;
		this.user = null;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public LoginStatus getStatus() {
		return status;
	}

	public void setStatus(LoginStatus status) {
		this.status = status;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
	
}
