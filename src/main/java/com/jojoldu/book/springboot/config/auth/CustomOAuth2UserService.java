package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

//구글 로그인 이후 가져온 사용자의 정보(email, name, picture등)들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능 지원 클래스

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //현재 로그인 진행중인 서비스
        //네이버인지 구글인지 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //OAuth2 로그인 진행 시 키가 되는 필드 값.  Primary Key와 같은 의미
        //구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원하지 않음
        //구글의 기본 코드는 sub
        //네이버 로그인과 구글 로그인을 동시 지원할 때 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //OAuthAttributes
        //- OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        //SessionUser
        //- 세션에 사용자 정보를 저장하기 위한 dto 클래스
        //- User 클래스를 쓰지 않고 새로 만들어서 쓰는 이유 : User 클래스를 세션에 저장하려하면 직렬화를 구현하지 않았다는 의미의 에러가 뜸
        //- User 클래스가 엔티티이기 때문. 엔티티 클래스는 언제 다른 엔티티와 관계가 형셩될 지 모름
        //- 자식 엔티티를 가지고 있다면 직렬화 대상에 자식들까지 포함되어 성능 이슈, 부수 효과가 발생할 확률이 높음
        //-> 직렬화 기능을 가진 세션 Dto 사용하는 것이 운영 및 유지보수에 도움
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    //구글 사용자 정보가 업데이트 되었을 때를 대비
    //사용자 name이나 pictrue이 변경되면 User 엔티티에도 반영됨
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
