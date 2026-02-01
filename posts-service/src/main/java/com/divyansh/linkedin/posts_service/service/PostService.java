package com.divyansh.linkedin.posts_service.service;

import com.divyansh.linkedin.posts_service.auth.UserContextHolder;
import com.divyansh.linkedin.posts_service.dto.PostCreateRequestDTO;
import com.divyansh.linkedin.posts_service.dto.PostDTO;
import com.divyansh.linkedin.posts_service.entity.Post;
import com.divyansh.linkedin.posts_service.event.PostCreatedEvent;
import com.divyansh.linkedin.posts_service.exception.ResourceNotFoundException;
import com.divyansh.linkedin.posts_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, Object> kafkaTemplate;

    public PostDTO createPost(PostCreateRequestDTO postCreateRequestDTO) {
        Long userId = UserContextHolder.getCurrentUserId();
        Post post = modelMapper.map(postCreateRequestDTO, Post.class);
        post.setUserId(userId);

        Post savedPost = postRepository.save(post);

        PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder()
                .postId(savedPost.getId())
                .creatorId(userId)
                .content(savedPost.getContent())
                .build();

        kafkaTemplate.send("post-created-topic", postCreatedEvent);


        return modelMapper.map(savedPost,PostDTO.class);
    }

    public PostDTO getPostById(Long postId) {
        log.debug("Retrieving post with id: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found!"));
        return modelMapper.map(post,PostDTO.class);
    }

    public List<PostDTO> getAllPostsForUser(Long userId) {
        log.info("Getting All Posts for user with id: {}",userId);
        List<Post> posts = postRepository.findByUserId(userId);
        return posts
                .stream()
                .map((element) -> modelMapper.map(element, PostDTO.class))
                .toList();
    }
}
