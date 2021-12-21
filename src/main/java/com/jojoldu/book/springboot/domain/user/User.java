package com.jojoldu.book.springboot.domain.user;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class User extends BaseTimeEntity {

    @Id //해당 테이블의 PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK의 생성 규칙
    private Long id;

    //@Column
    //테이블의 칼럼. 생략 가능
    //기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    //Enum - 열거형, 서로 연관된 상수들의 집합
    //JPA로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지를 결정
    //기본적으로는 int로 된 숫자가 저장
    //숫자로 저장되면 데이터베이스로 확인할 때 그 값이 무슨 코드를 의미하는지 알 수가 없음
    //그래서 문자열(EnumType.STRING)로 저장될 수 있도록 선언함
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder //해당 클래스의 빌더 패턴 클래스를 생성. 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함함
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
