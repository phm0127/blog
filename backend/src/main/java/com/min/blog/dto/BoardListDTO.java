package com.min.blog.dto;

import com.min.blog.model.Board;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDTO {
    private List<Board> boards;

    private long totalPage;

    private int pageIndex;
}
