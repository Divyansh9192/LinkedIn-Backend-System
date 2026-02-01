package com.divyansh.linkedin.posts_service.controller;

import com.divyansh.linkedin.posts_service.dto.PostCreateRequestDTO;
import com.divyansh.linkedin.posts_service.dto.PostDTO;
import com.divyansh.linkedin.posts_service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostsController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequestDTO postCreateRequestDTO){

        PostDTO createdPost = postService.createPost(postCreateRequestDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        PostDTO postDTO = postService.getPostById(postId);
        return ResponseEntity.ok(postDTO);
    }
    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(@PathVariable Long userId){
        List<PostDTO> posts = postService.getAllPostsForUser(userId);
        return ResponseEntity.ok(posts);
    }
}
