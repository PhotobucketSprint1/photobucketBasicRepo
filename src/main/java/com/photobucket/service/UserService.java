package com.photobucket.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.photobucket.dao.*;
import com.photobucket.dao.CommentDao;
import com.photobucket.dao.FollowerDao;
import com.photobucket.dao.LikeDao;
import com.photobucket.dao.UserDao;
import com.photobucket.dto.UserDto;
import com.photobucket.exception.NotFoundException;
import com.photobucket.exception.UserNotFoundException;
import com.photobucket.model.Admin;
import com.photobucket.model.Comment;
import com.photobucket.model.Follower;
import com.photobucket.model.FriendReq;
import com.photobucket.model.Like;
import com.photobucket.model.Post;
import com.photobucket.model.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	PostDao postRepo;
	
	@Autowired
	CommentDao commentRepo;
	
	@Autowired
	FollowerDao followerRepository;
	
	@Autowired
	LikeDao likeDao;
	
	@Autowired
	FriendReqDao friendReqDao;
	
	
	public ResponseEntity<?> createUser(UserDto userDto) throws IOException {
		
		 User existingUser = userDao.findByUserName(userDto.getUserName());
	        if (existingUser != null) {
	            throw new NotFoundException("Username already exists");
	        }
	        existingUser = userDao.findByEmailId(userDto.getEmailId());
	        if (existingUser != null) {
	            throw new NotFoundException("Email already exists");
	        }
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setEmailId(userDto.getEmailId());
		user.setPassword(userDto.getPassword());
		byte[] compressedBytes = userDto.getProfilePicture().getBytes();
		user.setProfilePic(compressedBytes);
		user.setDeleted(false);
		user.setRole(userDto.getRole());
		userDao.save(user);
		return new ResponseEntity<String>("User Added",HttpStatus.OK);
	}
	
	
	public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

	
	public ResponseEntity<?> viewProfile(Long userid) {
	   	 Optional<User> user = userDao.findById(userid);
	   	 if(user.get().isBlocked()) {
	   		return new ResponseEntity<String>("Admin blocked your profile", HttpStatus.OK);
	   	 }
	   	UserDto userDto = new UserDto();
	   	 userDto.setEmailId(user.get().getEmailId());
	   	 userDto.setUserName(user.get().getUserName());
	   	 userDto.setId(user.get().getId());
	   	 userDto.setProfilePic(user.get().getProfilePic());
	   	 userDto.setPosts(user.get().getPosts());
	   	 System.out.println("USER "+user.get().getEmailId());
	   	 
	   	 return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);	
		}
	


public String editProfile(UserDto userDto, long userid){
    
		Optional<User> user = userDao.findById(userid);
		if(user.isEmpty()) {
			throw new NotFoundException("User Not Present");
		}
		if(user.get().isBlocked()) {
	   		return "Admin blocked your profile you are Unable to edit profile";
			
	   	 }
		user.get().setEmailId(userDto.getEmailId());
		user.get().setUserName(userDto.getUserName());
//	    user.get().setPassword(userDto.getPassword());
		userDao.save(user.get());
		return "Details are updated";
	}

public ResponseEntity<?> addProfilePic(MultipartFile img, long id) throws IOException {
	 
	 Optional<User> user = userDao.findById(id);
	 if(user.isEmpty()) {
		 throw new NotFoundException("User Not Found !");
	 }
	 if(user.get().isBlocked()) {
	   		return new ResponseEntity<String>("Admin blocked your profile", HttpStatus.OK);
	  }
	 if(img.getSize() > 5000000) {
		 return new ResponseEntity<String>("Maximum size reached",HttpStatus.OK);		 
	 }
	 if(user.get().getId() > 0) {
		 user.get().setProfilePic(img.getBytes());
		 userDao.save(user.get());
		 return new ResponseEntity<String>("Profile Pic Changed",HttpStatus.OK);
	 }else {
		 return new ResponseEntity<String>("User not found !", HttpStatus.ACCEPTED);
	 }
}

public boolean validateUser(String username, String password)  {
	User user = userDao.findByUserName(username);
	if (user != null&&user.getPassword().equals(password)&&user.getUserName().equals(username))
	{ 
	   return true;
	}	
	return false;
}

public void follow(User follower, User following) {
    if (follower.getId()==following.getId()) {
        throw new IllegalArgumentException("Cannot follow oneself");
    }
    Follower existingFollower = followerRepository.findByFollowerAndFollowing(follower, following);
    if (existingFollower != null) {
        throw new IllegalArgumentException("Already following");
    }
    Follower newFollower = new Follower();
    newFollower.setFollower(follower);
    newFollower.setFollowing(following);
    followerRepository.save(newFollower);
}

public void unfollow(User follower, User following) {
    Follower existingFollower = followerRepository.findByFollowerAndFollowing(follower, following);
    if (existingFollower == null) {
        throw new IllegalArgumentException("Not following");
    }
    followerRepository.delete(existingFollower);
}


public ResponseEntity<List<User>> getFollowers(User user) {
    List<Follower> followers = followerRepository.findByFollowing(user);
    List<User> result = new ArrayList<>();
    for (Follower follower : followers) {
        result.add(follower.getFollower());
    }
    return new ResponseEntity<List<User>>(result,HttpStatus.OK); 
}


public List<User> getFollowing(User user) {
    List<Follower> following = followerRepository.findByFollower(user);
    List<User> result = new ArrayList<>();
    for (Follower follower : following) {
        result.add(follower.getFollowing());
    }
    return result;
}

public ResponseEntity<?> addComment(Comment comment, long postId, long userId)  {
	Optional<Post> post = postRepo.findById(postId);
	Optional<User> user = userDao.findById(userId);
	
	if(post.isEmpty()) {
		throw new NotFoundException("Post Not Found !");
	}else if(user.isEmpty()) {
		throw new NotFoundException("User not found !");
	}
	
	List<Comment> cms = post.get().getComments();
//	cms.add(comment);	
	Comment com = new Comment();
	com.setPost(post.get());
	com.setUser(user.get());
	com.setText(comment.getText());
	cms.add(com);
	post.get().setComments(cms);
	commentRepo.save(com);
	return new ResponseEntity<String>("",HttpStatus.OK);
	
}

	public ResponseEntity<?> likePost(long postId,long userId) {
		Optional<User> user=userDao.findById(userId);
		Optional<Post> post=postRepo.findById(postId);
		if(user.isEmpty()) {
			throw new NotFoundException("User Not Found !");
		}else if(post.isEmpty()) {
			throw new NotFoundException("Post Not Found !");
		}
		
		Like like=new Like();
		like.setPost(post.get());
		like.setUser(user.get());
		likeDao.save(like);
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
	
	public FriendReq sendFriendRequest(FriendReq req) {
		FriendReq newReq = new FriendReq();
		System.out.println(req.getSender());
		newReq.setSender(req.getSender());
		newReq.setReciever(req.getReciever());
		newReq.setReqDate(LocalDate.now());
		friendReqDao.save(newReq);
		return newReq;
	}

	public FriendReq acceptFriendRequest(FriendReq req) {
	FriendReq bean = friendReqDao.findById(req.getFriendReqId()).get();
		bean.setAccepted(true);
		bean.setStatus("Accepted");
		friendReqDao.save(bean);
		return bean;
	}
	
	public List<FriendReq> viewAllReceivedFriendReqest(){
		return friendReqDao.findAll();
	}
	
	public ResponseEntity<?> getComments(long postId){
		List<Comment> comList = commentRepo.searchCommentsByPost(postId);
		return new ResponseEntity<List<Comment>>(comList,HttpStatus.OK);
	}

}
