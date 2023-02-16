package com.photobucket.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.photobucket.model.FriendReq;


	@Repository
	public interface FriendReqDao extends JpaRepository<FriendReq,Integer> {

	

}
