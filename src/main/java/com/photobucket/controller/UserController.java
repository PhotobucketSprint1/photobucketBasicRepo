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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.photobucket.dao.FriendReqDao;
import com.photobucket.dao.PostDao;

import com.photobucket.dao.UserDao;
import com.photobucket.dto.FriendReqDto;
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
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	FriendReqDao friendReqRepo;
	
	@Autowired
	PostDao postDao;
	
	 @Autowired
	    private FollowerDao followerRepository;

	@GetMapping("/homepage")
	public String welcomeMsg() {
		return "WELCOME TO PHOTO Bucket !";
	}
	@PostMapping(path="/addUser", consumes={MediaType.ALL_VALUE})
	public String addPet(User user) {
		userDao.save(user);
		return "ADDED User";
	}

	@PostMapping("/registerUser")
	public ResponseEntity<?> registerUser(@ModelAttribute UserDto userDto) throws IOException {
		userService.createUser(userDto);
		return new ResponseEntity<String>("User Created !", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	 public ResponseEntity<String> login(@RequestBody User loginCredentials){
		
		if(userService.isUserBlocked(loginCredentials.getUserName(), loginCredentials.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your Profile Is Blocked By Admin");
		}
			
		if (userService.validateUser(loginCredentials.getUserName(), loginCredentials.getPassword()))
		{
			return ResponseEntity.ok("Login successful.");
		}else
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid crdentials");
		}
	}
	
	@PostMapping("/createPost")
	 public ResponseEntity<?> createPost(@ModelAttribute PostDto postDto) throws IOException, PostServiceException {
		return postService.addPost(postDto);

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
	public String editProfileController(@RequestBody UserDto userDto, @PathVariable long userid){		
		return userService.editProfile(userDto, userid);
	}
	
	@PostMapping("/{userid}/addProfilePic")
	 public ResponseEntity<?> addProfilePic(@RequestParam("img") MultipartFile img, @PathVariable("userid") long userid) throws IOException{
		 return userService.addProfilePic(img, userid);
	 }
	
	@GetMapping("/getPostsByUser/{userId}")
    public List<PostDto> getPostsByUser(@PathVariable long userId) {
        return postService.getPostsByUser(userId);
    }
	
	@GetMapping("/getPostImage/{postId}")
    public PostDto getPostImage(@PathVariable long postId) {
        return postService.getPostImage(postId);
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
		return userService.addComment(comment, postId, userId);
	}
    
    @PutMapping("/likePost/{postId}/{userId}")
	public ResponseEntity<?> likePost(@PathVariable long postId,@PathVariable long userId ) {
		return userService.likePost(postId,userId);
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
	
	
	@GetMapping("/getUserByUsername/{username}")
    public long getUserByUserName(@PathVariable String username) {
        return postService.getUser(username);
    }
	
	@GetMapping("/getCommentsWithUsername")
	   public ResponseEntity<?> getcomment(){
		   return userService.getCommentsWithUserDetails();
	   }
	
	@GetMapping("{postId}/getLikes")
    public ResponseEntity<?>getlikes(@PathVariable long postId){
    	return userService.getLikes(postId);
    }
	
	@GetMapping("/following-users")
    public ResponseEntity<List<Post>> getFollowingUsersPosts(@RequestParam("userId") Long userId) {
        List<Post> posts = postService.getFollowingUsersPosts(userId);

        return ResponseEntity.ok(posts);
    }
	
	
	  @PostMapping("/{senderId}/sendFriendRequest/{receiverId}")
	    public ResponseEntity<FriendReq> sendFriendRequest(@PathVariable Long senderId, @PathVariable Long receiverId) {
		  FriendReq friendRequest = userService.sendFriendRequest(senderId, receiverId);
	        return ResponseEntity.ok(friendRequest);
	    }
	  
	 
	  @GetMapping("/pendingRequest/{receiverId}")
	    public ResponseEntity<List<FriendReqDto>> getPendingFriendRequests(@PathVariable Long receiverId) {
	        List<FriendReqDto> pendingRequests = userService.getPendingFriendRequests(receiverId);
	        return ResponseEntity.ok(pendingRequests);
	   }
	  
	  @PostMapping("/acceptFriendRequest/{senderId}/{recieverId}")
	    public ResponseEntity<?> acceptFriendRequest(@PathVariable Long senderId, @PathVariable Long recieverId) {
		  userService.acceptFriendRequest(senderId,recieverId);
		  return new ResponseEntity<String>("Friend Request Accepted ", HttpStatus.OK);
	    }
	  
	  @GetMapping("/getFriends/{userId}")
	    public ResponseEntity<List<User>> getFriends(@PathVariable Long userId) {
		  	User user = new User();
	        user.setId(userId);
	        List<User> friends = userService.getFriends(user);
	        return ResponseEntity.ok(friends);
	    }
	  

	  @GetMapping("/friendsPosts/{userId}")
	    public List<List<Post>> getPostsForAcceptedFriends(@PathVariable Long userId) {
	        User user = new User();
	        user.setId(userId);
	        return userService.getAcceptedFriends(user);
	        
	    }
	  
	  @GetMapping("/getCommentsForPost/{postId}")
	   public ResponseEntity<?> getcomment(@PathVariable Long postId){
		   return userService.getCommentForPostWithDetails(postId);
	   }
	  
}
