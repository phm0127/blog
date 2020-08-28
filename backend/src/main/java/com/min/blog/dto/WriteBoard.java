package com.min.blog.dto;

import com.min.blog.model.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WriteBoard {
    private int type;
    private String title;
    private String content;
    private long subCategoryID;

}
