package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 어플리케이션 동작에 필요한 객체의 생성과 연결을 담당하는 객체이다.
// 동작 하는 객체들에게 의존성을 주입해주는 역할을 한다.
// 객체를 실행하는 역할과 생성하고 연결하는 역할의 분리, 관심사 분리.
public class AppConfig {

    // 중복제거 리팩토링
    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    // 중복제거 리팩토링
    private DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }

    public MemberService memberService () {
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService () {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
}
