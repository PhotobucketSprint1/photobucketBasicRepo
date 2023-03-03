package com.photobucket.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.photobucket.dao.UserDao;
import com.photobucket.dto.UserDto;
import com.photobucket.model.Comment;
import com.photobucket.model.User;

@SpringBootTest
class UserServiceTest {
	@Autowired
	UserService userService;

	@Test
	void testCreateUser() throws IOException {
		MockMultipartFile mock = new MockMultipartFile("Mock","screen.png",MediaType.ALL_VALUE,"as".getBytes());
		UserDto user = new UserDto();
		user.setUserName("Venom");
		user.setEmailId("venom@gmail.com");
		user.setPassword("123");
		user.setRole("User");
		user.setProfilePicture(mock);
		assertEquals(200,userService.createUser(user).getStatusCodeValue());
	}
	
	@Test
	void testViewProfile() {
		long id=1;
	   assertEquals(200, userService.viewProfile(id).getStatusCodeValue());
	}

	@Test
	void testEditProfile() {	
		    UserDto userDto = new UserDto();	   
		    
		    long userid = 1;
		    String result = userService.editProfile(userDto, userid);
		    assertEquals("Details are updated", result);
	}

	@Test
	void testAddProfilePic() throws IOException{
	    MockMultipartFile mock = new MockMultipartFile("Mock","screen.png",MediaType.ALL_VALUE,"as".getBytes());
	    ResponseEntity<?> response = userService.addProfilePic(mock, 1);
	    assertNotNull(response);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertEquals("Profile Pic Changed", response.getBody());
	}
	
	@Test
	void testValidateUser() throws Exception {
		String username="Dharani";
		String password="WrrongPassword";
		boolean result=userService.validateUser(username, password);
		assertFalse(result);
	}
	
//	@Test
//	void testAddComment() {
//		Comment comment = new Comment();
//		long postId = 1; 
//		long userId = 2;
//		comment.setText("TEST COMMENT");
//		assertEquals(200,userService.addComment(comment, postId, userId).getStatusCodeValue());
//	}
	
	@Test
	void testAddLike() {
		long postId = 1;
		long userId = 2;
		assertEquals(200,userService.likePost(postId, userId).getStatusCodeValue());
	}

}
