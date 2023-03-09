package com.photobucket.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.photobucket.controller.UserController;
import com.photobucket.dao.PostDao;
import com.photobucket.dao.UserDao;
import com.photobucket.dto.UserDto;
import com.photobucket.exception.NotFoundException;
import com.photobucket.model.Comment;
import com.photobucket.model.Post;
import com.photobucket.model.User;


@SpringBootTest
class UserServiceTest {
	@Autowired
	UserService userService;
	
	@Mock
	PostDao postDao;
	
	@Mock
	UserController userController;
	
	private Comment comment;

    private Post post;

    private User user;
	
	
    public void setUp() {
        comment = new Comment();
        comment.setText("Test comment");

        post = new Post();
        post.setId(1L);
        post.setTitle("Test post");
        post.setComments(new ArrayList<>());

        user = new User();
        user.setId(1L);
        user.setUserName("testuser");
    }

	@Test
	void testCreateUser() throws IOException {
		MockMultipartFile mock = new MockMultipartFile("Mock","screen.png",MediaType.ALL_VALUE,"as".getBytes());
		UserDto user = new UserDto();
		Random rand = new Random();
		int randomNumber = rand.nextInt(101);
		
		user.setUserName("Venom"+randomNumber);
		user.setEmailId("venom"+randomNumber+"@gmail.com");
		user.setPassword("123");
		user.setRole("User");
		user.setProfilePicture(mock);
		assertEquals(200,userService.createUser(user).getStatusCodeValue());
	}
	
	@Test
	void testViewProfile() {
		long id=1;
		System.out.println("Order 2");
	   assertEquals(200, userService.viewProfile(id).getStatusCodeValue());
	}

	@Test
	void testEditProfile() {	
		    UserDto userDto = new UserDto();	   
		    userDto.setEmailId("edit@gmail.com");
		    userDto.setUserName("editpro");
		    long userid = 1;
		    String result = userService.editProfile(userDto, userid);
		    System.out.println("Order 3");
		    assertEquals("Details are updated", result);
	}

	@Test
	void testAddProfilePic() throws IOException{
	    MockMultipartFile mock = new MockMultipartFile("Mock","screen.png",MediaType.ALL_VALUE,"as".getBytes());
	    ResponseEntity<?> response = userService.addProfilePic(mock, 1);
	    assertNotNull(response);
	    System.out.println("Order 4");
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertEquals("Profile Pic Changed", response.getBody());
	}
	
	@Test
	void testValidateUser() throws Exception {
		String username="Dharani";
		String password="WrrongPassword";
		boolean result=userService.validateUser(username, password);
		System.out.println("Order 5");
		assertFalse(result);
	}
	
	@Test
	void testAddLike() {
		long postId = 1;
		long userId = 1;
		assertEquals(417,userService.likePost(postId, userId).getStatusCodeValue());
	}
	
	@Test
	void testGetCommentForPostWithDetails() {
		long postId = 1;
		System.out.println("Order 11");
		assertEquals(200, userService.getCommentForPostWithDetails(postId).getStatusCodeValue());
	}
	
}
