package com.min.blog.repository;

import com.min.blog.model.BoardType;
import com.min.blog.model.MainCategory;
import com.min.blog.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface MainCategoryRepository extends JpaRepository<MainCategory,Long> {
    Collection<MainCategory> findAllByType(BoardType type);
    Optional<MainCategory> findBySubcategories(SubCategory subCategory);
}
