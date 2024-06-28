package com.pro.tameit.groupTherapy.repo;

import com.pro.tameit.groupTherapy.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
