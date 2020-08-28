package com.min.blog.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardType {

    PORTFOLIO(0,"포트폴리오"),
    BLOG(1,"블로그");

    private final int key;
    private final String title;
}
