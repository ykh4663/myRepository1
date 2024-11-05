package Step1.change.api;

import Step1.change.domain.Order;
import Step1.change.dto.Result;
import Step1.change.dto.order.CreateOrderRequest;
import Step1.change.dto.order.CreateOrderResponse;
import Step1.change.dto.order.DeleteOrderResponse;
import Step1.change.dto.order.OrderDto;
import Step1.change.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/api/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse saveOrder(@RequestBody @Valid CreateOrderRequest request){
        Long id = orderService.order(request.getMemberId(), request.getItemId(), request.getCount());
        return new CreateOrderResponse(id);

    }

    @DeleteMapping("/api/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public DeleteOrderResponse deleteOrder(@PathVariable("id") Long id){
        orderService.cancelOrder(id);
        return new DeleteOrderResponse(id);
    }

    //주문 단건 조회
    @GetMapping("api/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result<OrderDto> orders_individual(@PathVariable("id")Long id){
        Order order = orderService.findOne(id);
        OrderDto orderDto = new OrderDto(order);
        return new Result<>(orderDto);


    }

    //주문 조회(페이징 처리 안하고 distinct join 사용)
    @GetMapping("api/orders")
    @ResponseStatus(HttpStatus.OK)
    public Result<List<OrderDto>> orders(){
        List<Order> orders = orderService.findAllWithMemberDeliveryAndItems();
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return new Result<>(collect);

    }
    //페이징 처리(lazy로딩 전략, batchsize이용)
    @GetMapping("api/orders/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDto> ordersWithPaging(Pageable pageable){
        Page<Order> page = orderService.findWithPaging(pageable);
        return page.map(OrderDto::new);


    }









}
