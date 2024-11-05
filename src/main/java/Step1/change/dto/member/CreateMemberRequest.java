package Step1.change.dto.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMemberRequest {
    @NotEmpty
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
