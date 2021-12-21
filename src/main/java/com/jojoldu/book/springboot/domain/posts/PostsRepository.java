package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//Posts 클래스로 Database를 접근하게 해줄 Repository
//보통 MyBatis 등에서 Dao라고 불리는 DB Layer 접근자
//-> JPA에서는 Repository라고 부르며 인터페이스로 생성
//JpaRepository<Entity 클래스, PK 타입>를 상속하면 기본적인 CRUD 메소드가 자동 생성됨
//Entity와 Entity Repository는 함께 위치해야함 -> domain 패키지에서 함께 관리
public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
