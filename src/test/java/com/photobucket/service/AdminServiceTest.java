package com.photobucket.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.photobucket.dto.AdminDto;
import com.photobucket.dto.CommentDto;
import com.photobucket.dto.PostDto;
import com.photobucket.dto.UserDto;
import com.photobucket.controller.AdminController;
import com.photobucket.dao.CommentDao;
import com.photobucket.dao.PostDao;
import com.photobucket.dao.UserDao;
import com.photobucket.model.Comment;
import com.photobucket.model.Post;
import com.photobucket.model.User;

@SpringBootTest
class AdminServiceTest {
	
    @Autowired
    AdminController adminController;
	
	@Autowired
	UserService userService;
    
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	PostService postService;
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	PostDao postDao;
	
	
//	@Test
	@Before(value = "")
	void createUserForTest() throws IOException {
		MockMultipartFile mock = new MockMultipartFile("Mock","screen.png",MediaType.ALL_VALUE,"as".getBytes());
		UserDto user = new UserDto();
		Random rand = new Random();
		int randomNumber = rand.nextInt(101);
		user.setUserName("Venom"+randomNumber);
		user.setEmailId("venom"+randomNumber+"@gmail.com");
		user.setPassword("123");
		user.setRole("User");
		user.setProfilePicture(mock);
		userService.createUser(user);
		
		
		MockMultipartFile mock1 = new MockMultipartFile("Mock","screen.png",MediaType.ALL_VALUE,"as".getBytes());
		PostDto post = new PostDto();
		post.setPostImg(mock1);
		post.setDescription("THIS IS DESCRIPTION");
		post.setTitle("NEW TITLEE");
		post.setUser_post_id(2);
		post.setImg(post.getPostImg().getBytes());
		postService.addPost(post);
		
		
	}
	
	@Test
	void testBlockUser() {
		 UserDto userDto = new UserDto();
		    userDto.setId(1);
		    assertEquals(200, adminService.blockUser(userDto).getStatusCodeValue());
	}

	@Test
	void testBlockComment() {
		CommentDto commentDto = new CommentDto();
        commentDto.setId((long) 1);
        assertEquals(200, adminService.blockComment(commentDto).getStatusCodeValue());
	}

	@Test
	void testBlockPost() {
		PostDto postDto = new PostDto();
        postDto.setId((long)1);
        System.out.println("order 1");
        assertEquals(200, adminService.blockPost(postDto).getStatusCodeValue());
	}

	@Test
	void testUnblockUser() {
		UserDto userDto = new UserDto();
        userDto.setId((long)1);
        assertEquals(200, adminService.unblockUser(userDto).getStatusCodeValue());
	}

	@Test
	void testUnblockComment() {
		CommentDto commentDto = new CommentDto();
        commentDto.setId((long) 1);
        assertEquals(200,  adminService.unblockComment(commentDto).getStatusCodeValue());
	}

	@Test
	void testUnblockPost() throws IOException {
		 PostDto postDto = new PostDto();
		 postDto.setId((long)1);
	     assertEquals(200,adminService.unblockPost(postDto).getStatusCodeValue());
	}

	@Test
	void testViewPostDetails() {
		assertEquals(200, adminService.viewPostDetails().getStatusCodeValue());
	}
	
	@Test
	void testFindAllUser() {
		assertEquals(200, adminService.findAllUser().getStatusCodeValue());
	}
	
	@Test
	void testSaveAdmin() {
		AdminDto adminDto=new AdminDto();
		adminDto.setUsername("newtestadmin");
		adminDto.setEmail_id("newtestad@gmail");
		adminDto.setPassword("125");
		adminDto.setAdmin_id(2);
		adminDto.setStatus(true);
		}

	@Test
	void testCreateAdmin() throws Exception {
		AdminDto adminDto=new AdminDto();
		Random rand = new Random();
		int randomNumber = rand.nextInt(101);
		adminDto.setUsername("takeadmin"+randomNumber);
		adminDto.setPassword("take");
		adminDto.setEmail_id("take"+randomNumber+"@gmail.com");
		String result=adminService.createAdmin(adminDto);
		assertEquals("Admin added successfully",result);
	}

	@Test
	void testValidateAdmin()  throws Exception{
		String username="Greesha";
		String password="wrongPassword";
		boolean result=adminService.validateAdmin(username, password);
		assertFalse(result);
	}
}
