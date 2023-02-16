package com.photobucket.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="likes")
public class Like {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    @JsonIgnore
	    private User user;

	    @ManyToOne
	    @JoinColumn(name = "post_id")
	    private Post post;
	    
	   
		public void setId(Long id) {
			this.id = id;
		}

		public Long getId() {
			return id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Post getPost() {
			return post;
		}

		public void setPost(Post post) {
			this.post = post;
		}
		
	    
}
