package com.min.blog.repository;

import com.min.blog.model.MainCategory;
import com.min.blog.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    Collection<SubCategory> findAllByMainCategory(MainCategory mainCategory);

}
