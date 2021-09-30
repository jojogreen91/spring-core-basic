package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // 구현체가 아닌 인터페이스(추상, 역할)에만 의존하도록 변경
    private final MemberRepository memberRepository;

    // 의존성 주입, 생성자 주입 방식 구현
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
