package com.photobucket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.photobucket.model.Comment;
import com.photobucket.model.Like;

@Repository
public interface LikeDao extends JpaRepository<Like, Long> {
	
	List<Like> findByPostId(long postId);
	
	@Query(value="select * from likes where post_id= :postId",nativeQuery = true)
	//public List<Like>searchLikesByPost(@Param("postId") long postId);
	List<Like> searchLikesByPost(@Param("postId") long postId);
	
	List<Like> findByPostIdAndUserId(Long postId, Long userId);
}
