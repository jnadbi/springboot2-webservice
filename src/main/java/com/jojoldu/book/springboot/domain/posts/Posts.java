package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Entity
//테이블과 링크될 클래스임을 나타냄
//@NoArgsConstructor
//기본 생성자 자동 추가. public Posts() {}와 같은 효과
//@Getter
//클래스 내의 모든 필드의 Getter 메소드를 자동 생성

//실제 DB의 테이블과 매칭될 클래스 (Entity 클래스)
//Entity클래스를 Request/Response 클래스로 사용해서는 안됨 -> dto를 따로 생성해서 사용할 것
//데이터베이스와 맞닿은 핵심 클래스
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {
    @Id //해당 테이블의 PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK의 생성 규칙
    private Long id;

    //@Column
    //테이블의 칼럼. 생략 가능
    //기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용

    @Column(length = 500, nullable = false) //사이즈를 500으로 늘림
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) //타입을 TEXT로 변경
    private String content;

    private String author;

    @Builder //해당 클래스의 빌더 패턴 클래스를 생성. 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //Entity 클래스에서는 Setter 메소드를 만들지 않는다
    //대신 해당 필드 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가함

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
