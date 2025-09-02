package com.spring3.domain.post.post.controller;

import com.spring3.domain.post.post.entity.Post;
import com.spring3.domain.post.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    private String getWriteFormHtml(String errorMessage,String title,String content){
        return """
                <div style="color:red">%s</div>
                <form method="POST" action="/posts/doWrite">
                  <input type="text" name="title" value="%s" autoFocus="true">
                  <br>
                  <textarea name="content" value="%s"></textarea>
                  <br>
                  <input type="submit" value="작성">
                </form>
                """.formatted(errorMessage,title,content);
    }

    @GetMapping("/posts/write")
    @ResponseBody
    public String write() {

        return getWriteFormHtml("","","");
    }

    @PostMapping("/posts/doWrite")
    @ResponseBody
    public String doWrite(
            String title,
            String content
    ) {

        if(title.isBlank()) return getWriteFormHtml("제목을 입력해주세요.",title,content);
        if(content.isBlank()) return getWriteFormHtml("내용을 입력해주세요",title,content);


        Post post = postService.write(title, content);

        return "%d번 글이 작성되었습니다.".formatted(post.getId());
    }

}