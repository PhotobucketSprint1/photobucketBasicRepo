package com.photobucket.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity.BodyBuilder;

import com.photobucket.dao.FollowerDao;
import com.photobucket.dao.PostDao;

import com.photobucket.dao.UserDao;
import com.photobucket.dto.PostDto;
import com.photobucket.dto.UserDto;
import com.photobucket.model.Comment;
import com.photobucket.model.FriendReq;
import com.photobucket.model.Like;
import com.photobucket.model.Post;

import com.photobucket.model.User;
import com.photobucket.service.PostService;
import com.photobucket.service.PostServiceException;
import com.photobucket.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDao userDao;
	
	 @Autowired
	    private FollowerDao followerRepository;

	@GetMapping("/homepage")
	public String welcomeMsg() {
		return "WELCOME TO PHOTO Bucket !";
	}

	@PostMapping("/registerUser")
	public ResponseEntity<?> registerUser(@ModelAttribute UserDto userDto) throws IOException {
		userService.createUser(userDto);
		return new ResponseEntity<String>("User Created !", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	 public ResponseEntity<String> login(@RequestBody User loginCredentials){
		if (userService.validateUser(loginCredentials.getUserName(), loginCredentials.getPassword()))
		{
			return ResponseEntity.ok("Login successful.");
		} 
		else
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid crdentials");
		}
	}
	
	@PostMapping("/createPost")
	 public ResponseEntity<?> createPost(@ModelAttribute PostDto postDto) throws IOException, PostServiceException {
       postService.addPost(postDto);
		return new ResponseEntity<String>("Post Created", HttpStatus.CREATED);
   }
	
	@PutMapping("/changePostPicture/{postid}")
	public ResponseEntity<?> changePicture(@RequestParam("changeImg") MultipartFile changeFile, @PathVariable("postid") long id ) throws IOException, PostServiceException {
		postService.changePicture(id, changeFile);
		return new ResponseEntity<String>("Post Picture Updated !", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getPost/{postid}")
	public ResponseEntity<?> getPost(@PathVariable("postid") long post_id){
		return postService.getPost(post_id);
	}
	
	@DeleteMapping("/deletePost/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable long postId) throws PostServiceException{
		postService.deletePost(postId);
		return new ResponseEntity<String>("Post Deleted", HttpStatus.CREATED);
	}

	@GetMapping("/viewprofile/{userid}")
    public ResponseEntity<?> viewProfileController(@PathVariable Long userid) {  	
    	 return userService.viewProfile(userid);
    }
	
	@PutMapping("/editprofile/{userid}")
	public String editProfileController(@RequestBody UserDto userDto){		
		return userService.editProfile(userDto);
	}
	
	@PostMapping("/{userid}/addProfilePic")
	 public ResponseEntity<?> addProfilePic(@RequestParam("img") MultipartFile img, @PathVariable("userid") long userid) throws IOException{
		 return userService.addProfilePic(img, userid);
	 }
	
	@PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<?> follow(@PathVariable Long followerId, @PathVariable Long followingId) {
        User follower = new User();
        follower.setId(followerId);
        User following = new User();
        following.setId(followingId);
        userService.follow(follower, following);
        return new ResponseEntity<String>(followerId+" is Following "+followingId, HttpStatus.OK);
    }

    @PostMapping("/{followerId}/unfollow/{followingId}")
    public ResponseEntity<?> unfollow(@PathVariable Long followerId, @PathVariable Long followingId) {
        User follower = new User();
        follower.setId(followerId);
        User following = new User();
        following.setId(followingId);
        userService.unfollow(follower, following);
        return new ResponseEntity<String>(followerId+" is Unfollowing "+followingId, HttpStatus.OK);
    }
    
    @PutMapping("/addComment/{postId}/{userId}")
	public ResponseEntity<?> addComment(@RequestBody Comment comment, @PathVariable long postId, @PathVariable long userId) {
		userService.addComment(comment, postId, userId);
		return new ResponseEntity<String>("Comment Added On Post "+postId, HttpStatus.OK);
	}
    
    @PutMapping("/likePost/{postId}/{userId}")
	public ResponseEntity<?> likePost(@PathVariable long postId,@PathVariable long userId ) {
		userService.likePost(postId,userId);
		return new ResponseEntity<String>("Your Like is added.. to Post Id : "+postId, HttpStatus.OK);
	}
    
    @PostMapping("/sendFriendRequest")
	public ResponseEntity<?> sendFriendRequest(@RequestBody FriendReq req){
		userService.sendFriendRequest(req);
		return new ResponseEntity<String>("Friend Request Sent ", HttpStatus.OK);
	}
	
	@PutMapping("/acceptFriendRequest")
	public ResponseEntity<?> acceptFriendRequest(@RequestBody FriendReq req){
		userService.acceptFriendRequest(req);
		return new ResponseEntity<String>("Friend Request Accepted ", HttpStatus.OK);
	}
	
	@GetMapping("/viewAllReceivedFriendReqest")
	public List<FriendReq> viewAllReceivedFriendReqest() {
		return userService.viewAllReceivedFriendReqest();
	}
	
	@GetMapping("/getFollowers")
	public ResponseEntity<?> viewFollower(@RequestBody User user){
		return userService.getFollowers(user);
	}
	
	@GetMapping("/getComments/{postId}")
	public ResponseEntity<?> viewLikes(@PathVariable long postId){
		return userService.getComments(postId);
//		return new ResponseEntity<String>("Friend Request Accepted ", HttpStatus.OK);
	}
	
	
}
