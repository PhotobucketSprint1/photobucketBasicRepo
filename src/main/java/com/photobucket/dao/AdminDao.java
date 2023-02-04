package com.photobucket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.photobucket.model.Admin;
import com.photobucket.model.User;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {

}
