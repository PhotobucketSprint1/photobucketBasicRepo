package com.photobucket.service;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
	CommentDao commentDao;
	
	@Autowired
	PostDao postDao;
	
	@Test
	void testBlockUser() {
		 UserDto userDto = new UserDto();
		    userDto.setId(2);
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
        assertEquals(200, adminService.blockPost(postDto).getStatusCodeValue());
	}

	@Test
	void testUnblockUser() {
		UserDto userDto = new UserDto();
        userDto.setId(1);
        assertEquals(200, adminService.unblockUser(userDto).getStatusCodeValue());
	}

	@Test
	void testUnblockComment() {
		CommentDto commentDto = new CommentDto();
        commentDto.setId((long) 1);
        assertEquals(200,  adminService.unblockComment(commentDto).getStatusCodeValue());
	}

	@Test
	void testUnblockPost() {
		 PostDto postDto = new PostDto();
		 postDto.setId((long) 1);
	     assertEquals(200,adminService.unblockPost(postDto).getStatusCodeValue() );
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
		adminDto.setUsername("newAdmin");
		adminDto.setPassword("ani");
		adminDto.setEmail_id("newAd@gmail.com");
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
