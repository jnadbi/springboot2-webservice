package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.LoginUser;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        //Model
        //서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
        //postsService.findAllDesc()의 결과를 posts로 index.mustache에 전달
        model.addAttribute("posts", postsService.findAllDesc());

        //세션에 저장된 값이 있을 때만 model에 userName 등록
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        //머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때
        // 앞의 경로(src/main/resources/templates)와 뒤의 파일 확장자(.mustache)는 자동으로 지정됨
        return "index";
        // -> src/main/resources/templates/index.mustache로 전환되어 View Resolver가 처리함
    }

//    Login - 어노테이션 구현 전
//    private final HttpSession httpSession; //Login

//    @GetMapping("/")
//    public String index(Model model) {
//        //Model
//        //서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
//        //postsService.findAllDesc()의 결과를 posts로 index.mustache에 전달
//        model.addAttribute("posts", postsService.findAllDesc());
//
//        //Login
//        //CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//
//        //세션에 저장된 값이 있을 때만 model에 userName 등록
//        if (user != null) {
//            model.addAttribute("userName", user.getName());
//        }
//
//        //머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때
//        // 앞의 경로(src/main/resources/templates)와 뒤의 파일 확장자(.mustache)는 자동으로 지정됨
//        return "index";
//        // -> src/main/resources/templates/index.mustache로 전환되어 View Resolver가 처리함
//    }


    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }


    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }


}
