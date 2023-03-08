package com.photobucket.dao;


import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.photobucket.enus.RequestStatus;
import com.photobucket.model.FriendReq;
import com.photobucket.model.User;
//reciever

	@Repository
	public interface FriendReqDao extends JpaRepository<FriendReq,Integer> {

		List<FriendReq> findByRecieverId(Long receiverId);
		List<FriendReq> findBySender(User sender);
		
		List<FriendReq> findBySenderOrReciever(User sender, User receiver);

	    Optional<FriendReq> findBySenderAndReciever(User sender, User receiver);
	    
	    List<FriendReq> findByRecieverAndStatus(User receiver, RequestStatus status);
	    
	    List<User> findBySenderId(Long userId);
	    
//	    public List<User> findFriendsByUserId(Long userId) {
//	        String jpql = "SELECT f.reciever FROM FriendReq f WHERE f.sender.id = :userId AND f.status = 'ACCEPTED'";
//	        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
//	        query.setParameter("userId", userId);
//	        return query.getResultList();
//	      }
	
	    

}
