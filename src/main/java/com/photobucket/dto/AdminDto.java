package com.photobucket.dto;

public class AdminDto {
private long admin_id;
	
	private String email_id;
	private String password;
	private String username;
	private boolean isStatus;
	
	
	public boolean isStatus() {
		return isStatus;
	}
	public void setStatus(boolean isStatus) {
		this.isStatus = isStatus;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(long admin_id) {
		this.admin_id = admin_id;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
