package com.back.domain.post.post.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

//    @GetMapping("/api/v1/posts")
//    @ResponseBody
    @GetMapping
    public List<Post> getItems() {
        List<Post> items = postService.findAll();

        return items;
    }

    @GetMapping("/{id}")
    public Post getItem(@PathVariable int id) {
        Post post = postService.findById(id).get();

        return post;
    }
}