package Step1.change.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //cascade쓰는 경우 : order가 관리한다고 생각하는 것들은 붙이면 order만 persist해줘도 persist되는 편리함
    //다른데서는 orderItem, delivery안써야함 오직 order에서만 사용한다 할 때 쓰면된다
    //다른 곳에서도 이를 사용하는 경우 별도의 리포지토리를 파서 persist해주는게 맞다
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)//string 꼭 적기
    private OrderStatus status;

    public void addMember(Member member){
        this.member = member;
        member.changeOrder(this);
    }
    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }
    public void makeDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.changeOrder(this);

    }
    public static Order createOrder(Member member, Delivery delivery, OrderItem...orderItems){
        Order order = new Order();
        order.addMember(member);
        order.makeDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.orderDate = LocalDateTime.now();
        order.status = OrderStatus.ORDER;
        return order;
    }

    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다");
        }
        status = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }






}
