package com.pro.tameit.groupTherapy.services;

import com.pro.tameit.groupTherapy.dto.PostDto;
import com.pro.tameit.groupTherapy.models.Post;

import java.util.List;

public interface PostService {
    //Create
    String createPost(String text);

    //Read
    List<PostDto> readAcceptedPosts();

    List<PostDto> readPendingPosts();

    //Update Or Accept Post -Admin UI-
    String acceptPost(Long id);

    //Delete -Admin UI-
    String deletePost(Long id);

    PostDto mapToPostDto(Post post);
}
