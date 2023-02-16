package com.photobucket.model;

	import java.time.LocalDate;


	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.JoinColumn;
	import javax.persistence.ManyToOne;
	import javax.persistence.OneToOne;
    import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
	import com.fasterxml.jackson.databind.annotation.JsonSerialize;
	import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
	import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

	@Entity
	@Table(name="friend_req")
	public class FriendReq {
		
		@Id
		@GeneratedValue
		private int friendReqId;
		
		@ManyToOne
		@JoinColumn(name="senderId")
		@JsonIgnore
		private User sender;
		
		@ManyToOne
		@JoinColumn(name="recieverId" )
		@JsonIgnore
		private User reciever;
		
		//@JsonDeserialize(using = LocalDateDeserializer.class)
		//@JsonSerialize(using = LocalDateSerializer.class)
		//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") //2021-01-29
		private LocalDate reqDate;
		
		private boolean isAccepted;
		private String status;


		public int getFriendReqId() {
			return friendReqId;
		}

		public void setFriendReqId(int friendReqId) {
			this.friendReqId = friendReqId;
		}

		public User getSender() {
			return sender;
		}

		public void setSender(User sender) {
			this.sender = sender;
		}

		public User getReciever() {
			return reciever;
		}

		public void setReciever(User reciever) {
			this.reciever = reciever;
		}

		public LocalDate getReqDate() {
			return reqDate;
		}

		public void setReqDate(LocalDate reqDate) {
			this.reqDate = reqDate;
		}

		public boolean isAccepted() {
			return isAccepted;
		}

		public void setAccepted(boolean isAccepted) {
			this.isAccepted = isAccepted;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

}
