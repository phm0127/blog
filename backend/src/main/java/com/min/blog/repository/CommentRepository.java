package com.min.blog.repository;

import com.min.blog.model.Board;
import com.min.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Collection<Comment> findAllByBoard(Board board);
}
