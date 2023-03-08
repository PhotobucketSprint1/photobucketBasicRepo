package com.photobucket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.photobucket.model.*;

@Repository
public interface PostDao extends JpaRepository<Post, Long>{
	@Query("SELECT p FROM Post p JOIN p.user u JOIN u.sentRequests f WHERE f.id = :userId")
	List<Post> findPostsOfFollowingUsers(@Param("userId") Long userId);
	
	List<Post> findByUserIdIn(List<User> userIds);
	
	List<Post> findByUserId(Long userId);


}
