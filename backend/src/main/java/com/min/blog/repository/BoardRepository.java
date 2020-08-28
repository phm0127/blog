package com.min.blog.repository;

import com.min.blog.model.Board;
import com.min.blog.model.BoardType;
import com.min.blog.model.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Page<Board> findAllByType(BoardType type,Pageable pageable);
    Page<Board> findAllBySubCategory(SubCategory subCategory, Pageable pageable);
    Page<Board> findAllByTypeAndTitleContainingOrContentContaining(BoardType type, String containWord, String containWord2,Pageable pageable);
    Optional<Board> findById(Long id);

}
