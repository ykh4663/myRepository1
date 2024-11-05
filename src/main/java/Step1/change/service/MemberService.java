package Step1.change.service;

import Step1.change.Repository.MemberRepository;
import Step1.change.domain.Member;
import Step1.change.dto.member.UpdateMemberRequest;
import Step1.change.exception.item.ItemNotFoundException;
import Step1.change.exception.member.DuplicateMemberException;
import Step1.change.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        validateDuplicateName(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member>findMembers(){
        return memberRepository.findAll();
    }

    private void validateDuplicateName(Member member) {
        List<Member> findMember = memberRepository.findByName(member.getName());

        if(!findMember.isEmpty()){
            throw new DuplicateMemberException("이미 존재하는 회원입니다");
        }
    }

    @Transactional
    public void updateMemberName(Long id, UpdateMemberRequest request){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버가 존재하지 않습니다. ID: " + id));

        findMember.updateName(request.getName());
    }

    public Member findOne(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버가 존재하지 않습니다. ID: " + id));
    }




}
