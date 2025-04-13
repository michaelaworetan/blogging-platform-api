package com.example.bloggingPlatform.controller;

import com.example.bloggingPlatform.Service.PostService;
import com.example.bloggingPlatform.model.request.PostCreateRequest;
import com.example.bloggingPlatform.model.request.PostPageRequest;
import com.example.bloggingPlatform.model.response.PostListResponse;
import com.example.bloggingPlatform.model.response.PostResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest request) {
        PostResponse response = postService.createPost(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostListResponse> getAllPosts(
            @RequestParam(defaultValue = "0") int postPage,
            @RequestParam(defaultValue = "10") int postSize,
            @RequestParam(required = false) String postSearchTerm) {
        PostPageRequest pageRequest = PostPageRequest.builder()
                .postPage(postPage)
                .postSize(postSize)
                .term(postSearchTerm)
                .build();

        PostListResponse response = postService.getAllPosts(pageRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
