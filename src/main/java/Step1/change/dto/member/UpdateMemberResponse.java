package Step1.change.dto.member;

import Step1.change.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMemberResponse {
    private Long id;
    private String name;

    public UpdateMemberResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
    }
}
