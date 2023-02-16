package com.photobucket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photobucket.model.Comment;
import com.photobucket.model.Follower;
import com.photobucket.model.User;

public interface FollowerDao extends JpaRepository<Follower, Long> {

	List<Follower> findByFollower(User follower);
    List<Follower> findByFollowing(User following);
	Follower findByFollowerAndFollowing(User follower, User following);
}
