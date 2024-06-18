package com.pro.tameit.groupTherapy.services;

public interface CommentService {
    //Create
    String createComment(Long id, String text);

    //Delete -Admin UI-
    String deleteComment(Long id);
}
