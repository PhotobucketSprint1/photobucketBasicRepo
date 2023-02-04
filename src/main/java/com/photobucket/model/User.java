package com.photobucket.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String userName;
	private String password;
	private String emailId;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Post> posts;
	
	@OneToMany(mappedBy = "follower")
    private List<Follower> following;

    @OneToMany(mappedBy = "following")
    private List<Follower> followers;
    
//    @ManyToMany
//    @JoinTable(
//        name = "followers",
//        joinColumns = @JoinColumn(name = "follower_id"),
//        inverseJoinColumns = @JoinColumn(name = "following_id")
//    )
//    private List<User> followers;

	public User(String userName, String password, String emailId) {
		super();
		this.userName = userName;
		this.password = password;
		this.emailId = emailId;
	}

	public User() {
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
	

}
