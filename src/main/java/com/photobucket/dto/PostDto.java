package com.photobucket.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.web.multipart.MultipartFile;

import com.photobucket.model.Comment;
import com.photobucket.model.Like;
import com.photobucket.model.User;

public class PostDto {

	private long id;
	
	private String title;
	
	private String description;
	
	private byte[] img;
	
	private MultipartFile postImg;
	
	List<Comment> comments = new ArrayList<>();
	
	private User user;

	private List<Like> likes;
	
	private boolean isBlocked;
	
	private long user_post_id;
	
	private LocalDateTime dateTime;
	
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime localDateTime) {
		this.dateTime = localDateTime;
	}
	
	
	public long getUser_post_id() {
		return user_post_id;
	}

	public void setUser_post_id(long user_post_id) {
		this.user_post_id = user_post_id;
	}

	public MultipartFile getPostImg() {
		return postImg;
	}

	public void setPostImg(MultipartFile postImg) {
		this.postImg = postImg;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	
}
