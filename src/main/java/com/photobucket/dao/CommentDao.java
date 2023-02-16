package com.photobucket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.photobucket.model.*;
@Repository
public interface CommentDao extends JpaRepository<Comment, Long>{
	@Query(value="select * from comments where post_id= :postId",nativeQuery = true)
	public List<Comment>searchCommentsByPost(@Param("postId") long postId);
}
