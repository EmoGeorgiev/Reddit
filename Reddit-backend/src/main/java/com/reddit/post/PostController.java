package com.reddit.post;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        PostDto postDto = postService.getPost(id);
        return ResponseEntity
                .ok()
                .body(postDto);
    }

    @PostMapping
    public ResponseEntity<PostDto> addPost(@RequestBody @Valid PostDto postDto) {
        PostDto resultPostDto = postService.addPost(postDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultPostDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody @Valid PostDto postDto) {
        PostDto updatedPostDto = postService.updatePost(id, postDto);
        return ResponseEntity
                .ok()
                .body(updatedPostDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
