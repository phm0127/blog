package com.min.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditBoard {
    private long boardID;
    private String title;
    private String content;
    private long subCategoryID;
}
