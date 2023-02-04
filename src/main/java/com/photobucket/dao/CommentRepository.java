package com.photobucket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.photobucket.model.*;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
