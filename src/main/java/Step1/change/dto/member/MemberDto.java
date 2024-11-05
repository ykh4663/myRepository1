package Step1.change.dto.member;

import Step1.change.domain.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private String name;
    private String city;

    public MemberDto(Member member) {
        this.name = member.getName();
        this.city = member.getAddress().getCity();
    }
}
