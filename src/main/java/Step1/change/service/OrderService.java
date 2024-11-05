package Step1.change.service;

import Step1.change.Repository.ItemRepository;
import Step1.change.Repository.MemberRepository;
import Step1.change.Repository.OrderRepository;
import Step1.change.domain.*;
import Step1.change.domain.item.Item;
import Step1.change.exception.item.ItemNotFoundException;
import Step1.change.exception.member.MemberNotFoundException;
import Step1.change.exception.order.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;



    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 ID의 멤버를 찾을 수 없습니다: " + memberId));
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("해당 ID의 아이템을 찾을 수 없습니다: " + itemId));
        Delivery delivery = new Delivery(findMember.getAddress(), DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(), count);
        Order order = Order.createOrder(findMember, delivery, orderItem);
        orderRepository.save(order);
        return order.getId();

    }


    @Transactional
    public void cancelOrder(Long orderId){
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("해당 ID의 주문을 찾을 수 없습니다: " + orderId));
        findOrder.cancel();
    }

    public List<Order> findAllWithMemberDelivery(){
        return orderRepository.findAllWithMD();

    }

    public List<Order> findAllWithMemberDeliveryAndItems(){
        return orderRepository.findAllWithMDI();

    }

    public Page<Order> findWithPaging(Pageable pageable){
        return orderRepository.findAllWithMDI_Paging(pageable);
    }

    public Order findOne(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("해당 주문이 존재하지 않습니다. ID: " + orderId));
    }
}
