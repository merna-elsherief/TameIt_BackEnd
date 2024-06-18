package com.pro.tameit.groupTherapy.services;

import com.pro.tameit.groupTherapy.models.Comment;
import com.pro.tameit.groupTherapy.models.Post;
import com.pro.tameit.groupTherapy.repo.CommentRepository;
import com.pro.tameit.groupTherapy.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    //Create
    @Override
    public String createComment(Long id, String text) {
        Post post = postRepository.findById(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        Comment comment = Comment.builder()
                .text(text)
                .post(post)
                .build();
        commentRepository.save(comment);
        if (post.getComments().isEmpty()){
            post.setComments(new ArrayList<>());
        }
        post.getComments().add(comment);
        postRepository.save(post);
        return "Comment Sent Successfully";
    }
    //Delete -Admin UI-
    @Override
    public String deleteComment(Long id){
        commentRepository.deleteById(id);
        return "Comment Deleted Successfully";
    }
}
