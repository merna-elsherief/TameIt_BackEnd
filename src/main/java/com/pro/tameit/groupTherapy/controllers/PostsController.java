package com.pro.tameit.groupTherapy.controllers;

import com.pro.tameit.groupTherapy.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;
    @GetMapping("/getAllAcceptedPosts")
    public ResponseEntity<?> getAllAcceptedPosts(){
        try {
            return new ResponseEntity<>(postService.readAcceptedPosts(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
//    -Admin UI-
    @GetMapping("/getAllPendingPosts")
    public ResponseEntity<?> getAllPendingPosts(){
        try {
            return new ResponseEntity<>(postService.readPendingPosts(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/sendPost")
    public ResponseEntity<String> sendPost(@RequestBody String text){
        try {
            return new ResponseEntity<>(postService.createPost(text), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //-Admin UI-
    @PostMapping("/acceptPost/{id}")
    public ResponseEntity<String> acceptPost(@PathVariable Long id){
        try {
            return new ResponseEntity<>(postService.acceptPost(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //-Admin UI-
    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        try {
            return new ResponseEntity<>(postService.deletePost(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
