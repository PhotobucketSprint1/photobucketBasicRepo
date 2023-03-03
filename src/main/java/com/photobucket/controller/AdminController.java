package com.photobucket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photobucket.dao.UserDao;
import com.photobucket.dto.AdminDto;
import com.photobucket.dto.CommentDto;
import com.photobucket.dto.PostDto;
import com.photobucket.dto.UserDto;
import com.photobucket.model.Admin;
import com.photobucket.model.User;
import com.photobucket.service.AdminService;
import com.photobucket.service.PostService;
import com.photobucket.service.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserDao userDao;
	
	
	@PostMapping("/registerAdmin")
	public String registerAdmin(@RequestBody AdminDto adminDto) 
	{
	 String msg = adminService.createAdmin(adminDto);
	     System.out.println(msg);
	     return msg;
	 }
	
	@PostMapping("/login")
	 public ResponseEntity<String> login(@RequestBody Admin loginCredentials){
		if (adminService.validateAdmin(loginCredentials.getUsername(), loginCredentials.getPassword())) {
			return ResponseEntity.ok("Login successful.");
		}else{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid crdentials");
		}
	}
	
	@GetMapping("/allUser")
	public ResponseEntity<?> getAllUser(){
		return adminService.findAllUser();
	}
	
	@GetMapping("/viewPostDetails")
	public ResponseEntity<?> viewPostDetails(){
    	return adminService.viewPostDetails();
    }
	
	@PostMapping("/blockUser")
	public ResponseEntity<?> blockUserController(@RequestBody UserDto userDto){
		return adminService.blockUser(userDto);
	}
	
	@PostMapping("/unblockUser")
	public ResponseEntity<?> unblockUserController(@RequestBody UserDto userDto){

		return adminService.unblockUser(userDto);
	}
	
	@PostMapping("/blockComment")
	public ResponseEntity<?> blockCommentController(@RequestBody CommentDto commentDto){
		return adminService.blockComment(commentDto);
	}
	
	@PostMapping("/unblockComment")
	public ResponseEntity<?> unblockCommentController(@RequestBody CommentDto commentDto){
		return adminService.unblockComment(commentDto);
	}
	
	@PostMapping("/blockPost")
	public ResponseEntity<?> blockPost(@RequestBody PostDto postDto){
		return adminService.blockPost(postDto);
    }
	
	@PostMapping("/unblockPost")
	public ResponseEntity<?> unblockPostController(@RequestBody PostDto postDto){
		return adminService.unblockPost(postDto);
	}
	
}
