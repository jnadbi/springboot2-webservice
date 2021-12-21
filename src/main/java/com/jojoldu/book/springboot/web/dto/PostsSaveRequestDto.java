package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Request와 Response용 Dto는 View를 위한 클래스라 정말 자주 변경이 필요
//View Layer와 DB Layer(Entity클래스?)의 역할 분리를 철저히 하는 것이 좋음
//Entity 클래스와 Controller에서 쓸 dto는 분리해서 사용
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
