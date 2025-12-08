package com.back.domain.post.postComment.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import com.back.domain.post.postComment.dto.PostCommentDto;
import com.back.domain.post.postComment.entity.PostComment;
import com.back.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class ApiV1PostCommentController {
    private final PostService postService;

    @GetMapping
    @Transactional(readOnly = true)
    public List<PostCommentDto> getItems(
            @PathVariable int postId
    ) {
        Post post = postService.findById(postId).get();

        return post
                .getComments()
                .stream()
                .map(PostCommentDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public PostCommentDto getItem(
            @PathVariable int postId,
            @PathVariable int id
    ) {
        Post post = postService.findById(postId).get();

        PostComment postComment = post.findCommentById(id).get();

        return new PostCommentDto(postComment);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public RsData<Void> delete(
            @PathVariable int postId,
            @PathVariable int id
    ) {
        Post post = postService.findById(postId).get();

        PostComment postComment = post.findCommentById(id).get();

        postService.deleteComment(post, postComment);

//        Map<String, Object> rsData = new LinkedHashMap<>();
//        rsData.put("resultCode", "200-1");
//        rsData.put("msg", "%d번 댓글이 삭제되었습니다.".formatted(postComment.getId()));

        return new RsData<>(
                "200-1",
                "%d번 댓글이 삭제되었습니다.".formatted(id)
        );
    }


//    댓글 수정
    record PostCommentModifyReqBody(
            @NotBlank
            @Size(min = 2, max = 100)
            String content
    ) {
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData<Void> modify(
            @PathVariable int postId,
            @PathVariable int id,
            @Valid @RequestBody PostCommentModifyReqBody reqBody
    ) {
        Post post = postService.findById(postId).get();

        PostComment postComment = post.findCommentById(id).get();

        postService.modifyComment(postComment, reqBody.content);

        return new RsData<>(
                "200-1",
                "%d번 댓글이 수정되었습니다.".formatted(id)
        );
    }
}