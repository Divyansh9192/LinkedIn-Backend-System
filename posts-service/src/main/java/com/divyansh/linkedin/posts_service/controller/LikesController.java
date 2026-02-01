package com.divyansh.linkedin.posts_service.controller;

import com.divyansh.linkedin.posts_service.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) throws BadRequestException {
        postLikeService.likePost(postId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) throws BadRequestException {
        postLikeService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }
}
