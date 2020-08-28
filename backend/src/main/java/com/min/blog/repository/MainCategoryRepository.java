package com.min.blog.repository;

import com.min.blog.model.BoardType;
import com.min.blog.model.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MainCategoryRepository extends JpaRepository<MainCategory,Long> {
    Collection<MainCategory> findAllByType(BoardType type);
}
