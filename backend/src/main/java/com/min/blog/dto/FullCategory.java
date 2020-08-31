package com.min.blog.dto;

import com.min.blog.model.MainCategory;
import com.min.blog.model.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FullCategory {
    private MainCategory mainCategory;
    private SubCategory subCategory;
}
