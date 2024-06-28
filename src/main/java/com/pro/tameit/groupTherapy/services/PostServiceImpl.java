package com.pro.tameit.groupTherapy.services;

import com.pro.tameit.groupTherapy.dto.CommentDto;
import com.pro.tameit.groupTherapy.dto.PostDto;
import com.pro.tameit.groupTherapy.PostStatus;
import com.pro.tameit.groupTherapy.models.Comment;
import com.pro.tameit.groupTherapy.models.Post;
import com.pro.tameit.groupTherapy.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    //Create
    @Override
    public String createPost(String text) {
        Post post = Post.builder()
                .text(text)
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);
        return "Sent Successfully";
    }
    //Read
    @Override
    public List<PostDto> readAcceptedPosts() {
        return postRepository.findAllAccepted().stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<PostDto> readPendingPosts() {
        return postRepository.findAllPending().stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }
    //Update Or Accept Post -Admin UI-
    @Override
    public String acceptPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new RuntimeException("Something Wrong Happened, Please Try Again!"));
        post.setStatus(PostStatus.ACCEPTED);
        postRepository.save(post);
        return "Accepted Successfully";
    }
    //Delete -Admin UI-
    @Override
    public String deletePost(Long id){
        postRepository.deleteById(id);
        return "Deleted Successfully";
    }
    @Override
    public PostDto mapToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setText(post.getText());
        if (post.getComments().isEmpty()){
            postDto.setComments(null);
        } else {
            List<Comment> comments = post.getComments();
            postDto.setComments(new ArrayList<>());
            for (Comment c:
                 comments) {
                CommentDto commentDto = new CommentDto(c.getId(), c.getText());
                postDto.getComments().add(commentDto);
            }
        }
        return postDto;
    }
}
