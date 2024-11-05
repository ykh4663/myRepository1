package Step1.change.dto.order;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long memberId;
    private Long itemId;
    private int count;

}
