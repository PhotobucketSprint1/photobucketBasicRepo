package com.photobucket.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.photobucket.dao.PostDao;
import com.photobucket.dao.UserDao;
import com.photobucket.dto.PostDto;
import com.photobucket.model.Post;
import com.photobucket.model.User;

@Service
public class PostService {
	
	
	@Autowired
	PostDao postRepo;
	
	@Autowired
	UserDao userDao;
	
	public ResponseEntity<?> deletePost(long id) throws PostServiceException {
		Optional<Post> post = postRepo.findById(id);
		if(post.get().getId()>0) {
			postRepo.delete(post.get());
		}else {
			throw new NoSuchElementException("Post Not Found !");
		}
		System.out.println("Post Deleted !");
		return new ResponseEntity<String>("Post Deleted", HttpStatus.OK);
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
	
	public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }
	
	public ResponseEntity<?> addPost(PostDto postDto) throws IOException {
		Post post = new Post();
		
		if(postDto.getPostImg().isEmpty()) {
			throw new java.lang.NullPointerException();
		}
		
		long user_post_id = postDto.getUser_post_id();
		Optional<User> user = userDao.findById(user_post_id);
		post.setDescription(postDto.getDescription());
		post.setTitle(postDto.getTitle());
		post.setUser(user.get());
		
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentTime = calendar.getTime();
		long time = currentTime.getTime();
		
		post.setDateTime(LocalDateTime.now());
		byte[] compressedBytes = postDto.getPostImg().getBytes();
		post.setImg(compressedBytes);
		postRepo.save(post);
		System.out.println("Post Saved !");
		return new ResponseEntity<String>("Post Saved", HttpStatus.OK);
		
	}
	
	public ResponseEntity<?> changePicture(long user_id, MultipartFile changeFile) throws IOException, PostServiceException {
		Optional<Post> post = postRepo.findById(user_id);
			
		if(changeFile.isEmpty()) {
			throw new java.lang.NullPointerException();
		}
		
			byte[] compressedBytes = changeFile.getBytes();
			post.get().setImg(compressedBytes);
			postRepo.save(post.get());
			System.out.println("Posted Picture Updated ! ");
			return new ResponseEntity<String>("Picture Updated For Post",HttpStatus.OK);
	}
	
	public ResponseEntity<?> getPost(long post_id) {
		Optional<Post> post = postRepo.findById(post_id);
			PostDto postDto = new PostDto();
			postDto.setDescription(post.get().getDescription());
			postDto.setTitle(post.get().getTitle());
			postDto.setUser_post_id(post.get().getUser().getId());
			postDto.setImg(post.get().getImg());
			postDto.setId(post.get().getId());
//			postDto.setComments(post.get().getComments());
			System.out.println("Post Fetched");
			return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	
}
