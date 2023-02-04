package com.photobucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.photobucket.dao.AdminDao;
import com.photobucket.dao.PostRepository;
import com.photobucket.dao.UserDao;
import com.photobucket.model.Admin;
import com.photobucket.model.Comment;
import com.photobucket.model.Post;
import com.photobucket.model.User;

@SpringBootApplication(scanBasePackages = "com.photobucket")
@EntityScan("com.photobucket.model")
@EnableJpaRepositories("com.photobucket.dao")
public class PhotobucketApplication {
	@Autowired UserDao u;
	@Autowired AdminDao ad;
	@Autowired
	private PostRepository postRepository;

	public static void main(String[] args) {
		SpringApplication.run(PhotobucketApplication.class, args);
	}

	 
}
