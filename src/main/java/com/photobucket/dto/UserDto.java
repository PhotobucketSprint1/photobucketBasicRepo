package com.photobucket.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.web.multipart.MultipartFile;

import com.photobucket.model.Follower;
import com.photobucket.model.Post;

public class UserDto {
	
	private long id;
	
	private String userName;
	private String password;
	private String emailId;
		
	private List<Post> posts;
	
    private List<Follower> following;

    private List<Follower> followers;
    
    private boolean isDeleted;
    private byte[] profilePic;
    private String role;
    
    private MultipartFile profilePicture;
    
    
    
	public MultipartFile getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(MultipartFile profilePicture) {
		this.profilePicture = profilePicture;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public List<Follower> getFollowing() {
		return following;
	}
	public void setFollowing(List<Follower> following) {
		this.following = following;
	}
	public List<Follower> getFollowers() {
		return followers;
	}
	public void setFollowers(List<Follower> followers) {
		this.followers = followers;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public byte[] getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
    
    
    

}
