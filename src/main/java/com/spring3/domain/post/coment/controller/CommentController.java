package com.spring3.domain.post.coment.controller;

import com.spring3.domain.post.post.entity.Post;
import com.spring3.domain.post.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;

    @AllArgsConstructor
    @Getter
    public static class CommentWriteForm{

        @NotBlank(message = "댓글 내용을 입력해주세요.")
        @Size(min =2 , max= 100 , message = "댓글 내용은 2글자 이상 100글자 이하로 입력해주세요.")
        private String content;
    }

    @PostMapping("/posts/{postId}/comments/write")
    @Transactional
    public String write(
            @PathVariable Long postId,
            @Valid CommentWriteForm form,
            Model model
    ) {
        Post post = postService.findById(postId).get();


        postService.writeComment(post, form.getContent());
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/posts/{postId}/comments/{commentId}/delete")
    @Transactional
    public String delete(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {

        Post post=postService.findById(postId).get();
        postService.deleteComment(post, commentId);

        return "redirect:/posts/" + postId;
    }
}
