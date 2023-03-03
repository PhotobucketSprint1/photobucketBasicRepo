package com.photobucket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.photobucket.model.Comment;
import com.photobucket.model.Like;

@Repository
public interface LikeDao extends JpaRepository<Like, Long> {
	
	List<Like> findByPostId(long postId);
}
