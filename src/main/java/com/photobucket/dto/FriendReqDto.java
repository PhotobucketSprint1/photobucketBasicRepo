package com.photobucket.dto;

import com.photobucket.enus.RequestStatus;
import com.photobucket.model.User;

public class FriendReqDto {

	 private User sender;
	 private RequestStatus status;
	 
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public RequestStatus getStatus() {
		return status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	 
}
