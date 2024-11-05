package Step1.change.service;

import Step1.change.Repository.MemberRepository;
import Step1.change.domain.Address;
import Step1.change.domain.Member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    void 회원가입(){
        Address address = new Address("ilsan", "gangsun", "123");
        Member member = new Member("nameA", address);
        Long joinId = memberService.join(member);
        Member findMember = memberRepository.findById(joinId).get();
        assertThat(member).isEqualTo(findMember);

    }

    @Test
    void 중복_회원가입(){
        Address address1 = new Address("ilsan", "gangsun", "123");
        Member member1 = new Member("nameA", address1);
        memberService.join(member1);

        Address address2 = new Address("ilsan", "gangsun", "123");
        Member member2 = new Member("nameA", address2);

        assertThatThrownBy(()->memberService.join(member2)).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 존재하는 회원입니다");





    }




}