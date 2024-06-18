package com.pro.tameit.groupTherapy.repo;

import com.pro.tameit.groupTherapy.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.status = 'ACCEPTED' ")
    List<Post> findAllAccepted();
    @Query("select p from Post p where p.status = 'PENDING' ")
    List<Post> findAllPending();
}
