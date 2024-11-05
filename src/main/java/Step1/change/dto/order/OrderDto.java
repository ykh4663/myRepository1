package Step1.change.dto.order;

import Step1.change.domain.Address;
import Step1.change.domain.Order;
import Step1.change.domain.OrderStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto{
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;
    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName(); // 연관된 Member 엔티티의 이름을 가져옴
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
        orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());
    }
}