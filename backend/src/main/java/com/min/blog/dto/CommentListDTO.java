package com.min.blog.dto;

import com.min.blog.model.Comment;
import com.min.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentListDTO {
    private Comment comment;
    private User user;
}
