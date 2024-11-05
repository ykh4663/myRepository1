package Step1.change.dto.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateMemberRequest {
    @NotEmpty
    private String name;
}
