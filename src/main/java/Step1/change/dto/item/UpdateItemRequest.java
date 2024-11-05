package Step1.change.dto.item;

import lombok.Data;

@Data
public class UpdateItemRequest {
    private String name;
    private int price;
    private int stockQuantity;
}
