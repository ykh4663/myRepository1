package Step1.change.api;

import Step1.change.domain.Address;
import Step1.change.domain.Member;
import Step1.change.dto.Result;
import Step1.change.dto.member.*;
import Step1.change.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("api/members")
    public ResponseEntity<Result<List<MemberDto>>> members(){
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream().map(MemberDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(new Result<>(collect));
    }

    @PostMapping("api/members")
    public ResponseEntity<CreateMemberResponse> saveMember(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member(request.getName(), new Address(request.getCity(), request.getStreet(), request.getZipcode()));

        Long id = memberService.join(member);
        return ResponseEntity.status(201).body(new CreateMemberResponse(id));
    }

    @PutMapping("api/members/{id}")
    public ResponseEntity<UpdateMemberResponse> updateMember(@RequestBody @Valid UpdateMemberRequest request,
                                             @PathVariable("id") Long id){
        memberService.updateMemberName(id, request);
        Member findMember = memberService.findOne(id);
        return ResponseEntity.ok(new UpdateMemberResponse(findMember));
    }




}
