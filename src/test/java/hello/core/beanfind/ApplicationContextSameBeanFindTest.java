package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {

    // static 내부 클래스로 테스트용 스프링 컨테이너 설정 클래스를 만들어준다.
    @Configuration
    static class SameBeanConfig {

        @Bean
        public MemberRepository memberRepository1 () {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2 () {
            return new MemoryMemberRepository();
        }
    }

    // 스프링 컨테이너 생성
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByTypeDuplicate () {
        // 예외 발생 검증
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByName () {
        MemberRepository memberRepository1 = ac.getBean("memberRepository1", MemberRepository.class);

        assertThat(memberRepository1).isInstanceOf(MemoryMemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType () {
        // 특정 타입의 빈을 모두 찾아서 Map<String, MemberRepository> 를 생성
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);

        // Map 의 key 값을 순회 하면서 value 값을 검증
        for (String key : beansOfType.keySet()) {
            assertThat(beansOfType.get(key)).isInstanceOf(MemoryMemberRepository.class);
        }
        // 몇개의 동일한 타입의 빈이 있는지 검증
        assertThat(beansOfType.size()).isEqualTo(2);
    }
}
