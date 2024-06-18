package com.pro.tameit.groupTherapy.controllers;

import com.pro.tameit.groupTherapy.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @PostMapping("/sendComment/{id}")
    public ResponseEntity<String> sendComment(@PathVariable Long id, @RequestBody String text){
        try {
            return new ResponseEntity<>(commentService.createComment(id, text), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id){
        try {
            return new ResponseEntity<>(commentService.deleteComment(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
