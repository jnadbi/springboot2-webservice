package com.jojoldu.book.springboot.config.auth.dto;

import com.jojoldu.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

//세션에 사용자 정보를 저장하기 위한 dto 클래스
//인증된 사용자 정보만 필요
//User 클래스를 쓰지 않고 새로 만들어서 쓰는 이유
// : User 클래스를 세션에 저장하려하면 직렬화를 구현하지 않았다는 의미의 에러가 뜸
//- User 클래스가 엔티티이기 때문. 엔티티 클래스는 언제 다른 엔티티와 관계가 형셩될 지 모름
//- 자식 엔티티를 가지고 있다면 직렬화 대상에 자식들까지 포함되어 성능 이슈, 부수 효과가 발생할 확률이 높음
//-> 직렬화 기능을 가진 세션 Dto 사용하는 것이 운영 및 유지보수에 도움

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
