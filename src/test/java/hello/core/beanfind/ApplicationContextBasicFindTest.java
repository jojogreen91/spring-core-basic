package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName () {
        // 빈 이름과 클래스 타입으로 빈 찾기
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        // 구현체 클래스의 인스턴스인지 검증
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름없이 타입으로 조회")
    void findBeanByType () {
        // 클래스 타입(인터페이스 타입으로)으로 빈 찾기
        MemberService memberService = ac.getBean(MemberService.class);

        // 구현체 클래스의 인스턴스인지 검증
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구현체 타입을 이용해서 조회")
    void findBeanBySpecificClass () {
        // 빈 이름과 클래스 타입으로 빈 찾기
        MemberServiceImpl memberServiceImpl = ac.getBean("memberService", MemberServiceImpl.class);

        // 구현체 클래스의 인스턴스인지 검증
        assertThat(memberServiceImpl).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름이 없는경우")
    void findBeanByNameFail () {
        // 스프링 컨테이너에 매칭되는 이름의 빈이 존재하지 않을 경우 검증
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("failTest", MemberService.class));
    }
}
