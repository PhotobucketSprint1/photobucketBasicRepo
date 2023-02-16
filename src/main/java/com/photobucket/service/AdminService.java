package com.photobucket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.photobucket.dao.AdminDao;
import com.photobucket.dao.CommentDao;
import com.photobucket.dao.PostDao;
import com.photobucket.dao.UserDao;
import com.photobucket.dto.AdminDto;
import com.photobucket.dto.CommentDto;
import com.photobucket.dto.PostDto;
import com.photobucket.dto.UserDto;
import com.photobucket.model.Admin;
import com.photobucket.model.Comment;
import com.photobucket.model.Post;
import com.photobucket.model.User;

@Service
public class AdminService {
	
	@Autowired
	PostDao postRepo;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AdminDao adminDao;
	
	@Autowired
	CommentDao commentRepo;
	
	public ResponseEntity<?> viewPostDetails(){
	    List<Post> postList = new ArrayList<>();
	    postList=postRepo.findAll();
	    System.out.println(postList.toString());
	  	ResponseEntity<List<Post>> responseEntity = new ResponseEntity<>(postList,HttpStatus.OK);
			return responseEntity;
	}
	
	public ResponseEntity<?> blockUser(UserDto userDto){
		Optional<User> user = userDao.findById(userDto.getId());
		user.get().setBlocked(true);
		userDao.save(user.get());
		System.out.println(user.get().getUserName());
		return new ResponseEntity<String>("User Blocked", HttpStatus.OK);
	}
	
	public ResponseEntity<?> unblockUser(UserDto userDto){
		Optional<User> user = userDao.findById(userDto.getId());
		user.get().setBlocked(false);
		userDao.save(user.get());
		System.out.println(user.get().getUserName());
		return new ResponseEntity<String>("User UnBlocked", HttpStatus.OK);	
	}
	
	public ResponseEntity<?> blockComment(CommentDto commentDto){
		
		Optional<Comment> comment = commentRepo.findById(commentDto.getId());
		comment.get().setBlocked(true);
		commentRepo.save(comment.get());
		System.out.println(comment.get().getText());
		return new ResponseEntity<String>("Comment Blocked", HttpStatus.OK);
	}
	
	public ResponseEntity<?> unblockComment( CommentDto commentDto){
		Optional<Comment> comment = commentRepo.findById(commentDto.getId());
		comment.get().setBlocked(false);
		commentRepo.save(comment.get());
		System.out.println(comment.get().getText());
		return new ResponseEntity<String>("Comment UnBlocked", HttpStatus.OK);
	}
	
	public ResponseEntity<?> blockPost(PostDto postDto){	
		Optional<Post> post = postRepo.findById(postDto.getId());
		post.get().setBlocked(true);
		postRepo.save(post.get());
		System.out.println(post.get().getDescription());
		return new ResponseEntity<String>("Post Blocked", HttpStatus.OK);
	}
	
	public ResponseEntity<?> unblockPost(PostDto postDto){
    	Optional<Post> post = postRepo.findById(postDto.getId());
   		post.get().setBlocked(false);
   		postRepo.save(post.get());
   		System.out.println(post.get().getDescription());
   		return new ResponseEntity<String>("Post UnBlocked", HttpStatus.OK);
   	}
	
	public ResponseEntity<?> findAllUser(){
		return new ResponseEntity<List<User>>(userDao.findAll(), HttpStatus.OK);
	}
	
	public String createAdmin(AdminDto adminDto) {
		Admin existingAdmin = adminDao.findByUsername(adminDto.getUsername());
        if (existingAdmin != null) {
            throw new RuntimeException("Username already exists");
        }
        
		Admin admin= new Admin();
		admin.setUsername(adminDto.getUsername());
		admin.setEmail_id(adminDto.getEmail_id());
		admin.setPassword(adminDto.getPassword());
		admin.setAdmin_id(adminDto.getAdmin_id());
		admin.setStatus(true);
		adminDao.save(admin);
		return "Admin added successfully";
	}
	
	public boolean validateAdmin(String username, String password)  {
		Admin admin = adminDao.findByUsername(username);
		if (admin != null&&admin.getPassword().equals(password)&&admin.getUsername().equals(username))
		{ 
		   return true;
		}	
		return false;	
	}
	
}