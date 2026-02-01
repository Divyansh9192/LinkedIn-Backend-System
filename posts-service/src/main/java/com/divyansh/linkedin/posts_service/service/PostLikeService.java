package com.divyansh.linkedin.posts_service.service;

import com.divyansh.linkedin.posts_service.auth.UserContextHolder;
import com.divyansh.linkedin.posts_service.entity.Post;
import com.divyansh.linkedin.posts_service.entity.PostLike;
import com.divyansh.linkedin.posts_service.event.PostLikedEvent;
import com.divyansh.linkedin.posts_service.exception.ResourceNotFoundException;
import com.divyansh.linkedin.posts_service.repository.PostLikeRepository;
import com.divyansh.linkedin.posts_service.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    private final KafkaTemplate<Long, Object> kafkaTemplate;

    public void likePost(Long postId) throws BadRequestException {
        log.info("Attempting to like the post with id: {}",postId);
        Long userId = UserContextHolder.getCurrentUserId();

        Post post = postRepository.findById(postId).orElseThrow(
                () ->new ResourceNotFoundException("Post not found with id: "+postId));

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(alreadyLiked) throw new BadRequestException("Cannot like the post twice");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
        log.info("Liked the post with id: {}",postId);

        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                .postId(postId)
                .likedByUserId(userId)
                .creatorId(post.getUserId())
                .build();

        kafkaTemplate.send("post-liked-topic",postId,postLikedEvent);

    }

    @Transactional
    public void unlikePost(Long postId) throws BadRequestException {
        log.info("Attempting to unlike the post with id: {}",postId);
        Long userId = UserContextHolder.getCurrentUserId();
        boolean exists = postRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with id: "+postId);

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(!alreadyLiked) throw new BadRequestException("Cannot unlike the post twice");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        log.info("Unliked the post with id: {}",postId);

    }
}
