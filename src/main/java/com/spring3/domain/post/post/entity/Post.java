package com.spring3.domain.post.post.entity;

import com.spring3.domain.post.coment.entity.Comment;
import com.spring3.global.jpa.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseEntity {
    private String title;
    private String content;

    @OneToMany(mappedBy = "post",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments=new ArrayList<>();

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Comment addComment(String content){
        Comment comment=new Comment(content, this);
        this.comments.add(comment);

        return comment;
    }

    public void deleteComment(Long commentId) {
        comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .ifPresent(comments::remove);

    }
}