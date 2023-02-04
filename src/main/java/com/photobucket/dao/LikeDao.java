package com.photobucket.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photobucket.model.Comment;
import com.photobucket.model.Like;

public interface LikeDao extends JpaRepository<Like, Long> {

}
