package Step1.change.domain;

import Step1.change.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public void changeOrder(Order order){
        this.order = order;
    }

    private int orderPrice;

    private int count;

    //생성메서드
    public static OrderItem createOrderItem(Item item, int price, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(price);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    private void setItem(Item item){
        this.item = item;
    }
    private void setOrderPrice(int orderPrice){
        this.orderPrice = orderPrice;
    }
    private void setCount(int count){
        this.count = count;
    }

    //비즈니스 로직
    public void cancel(){
        getItem().addStock(count);
    }

    //조회 로직

    /**
     * 주문상품 전체 가격 조회
     * @return
     */
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }





}
