package com.photobucket.service;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;

import com.photobucket.dto.PostDto;
import com.photobucket.model.*;

@SpringBootTest
class PostServiceTest {
	
	@Autowired
	PostService postService;
//
	@Test
	void testDeletePost() throws PostServiceException {
		long postId = 1;
		assertEquals(200,postService.deletePost(postId).getStatusCodeValue());
	}

	@Test
	void testAddPost() throws IOException, PostServiceException {
		MockMultipartFile mock = new MockMultipartFile("Mock","screen.png",MediaType.ALL_VALUE,"as".getBytes());
		PostDto post = new PostDto();
		post.setPostImg(mock);
		post.setDescription("THIS IS DESCRIPTION");
		post.setTitle("NEW TITLEE");
		post.setUser_post_id(1);
		post.setImg(post.getPostImg().getBytes());
		assertEquals(200,postService.addPost(post).getStatusCodeValue());
	}
	
	@Test
	void testChangePicture() throws IOException, PostServiceException {
		MockMultipartFile mock = new MockMultipartFile("Mock","screen.png",MediaType.ALL_VALUE,"as".getBytes());
		long id = 1;
		assertEquals(200,postService.changePicture(id, mock).getStatusCodeValue());
	}
	
	@Test
	void testGetPost() {
		long id = 1;
		assertEquals(200, postService.getPost(id).getStatusCodeValue());
		
	}
}
