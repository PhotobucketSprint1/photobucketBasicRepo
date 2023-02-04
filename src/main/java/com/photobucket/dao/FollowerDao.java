package com.photobucket.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photobucket.model.Comment;
import com.photobucket.model.Follower;

public interface FollowerDao extends JpaRepository<Follower, Long> {

}
