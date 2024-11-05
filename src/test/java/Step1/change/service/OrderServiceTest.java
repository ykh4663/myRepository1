package Step1.change.service;

import Step1.change.Repository.OrderRepository;
import Step1.change.domain.Member;

import Step1.change.domain.Order;
import Step1.change.domain.OrderItem;
import Step1.change.domain.item.Book;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void 주문_성공(){
        Member member1 = new Member("member1", null);
        em.persist(member1);
        Book book1 = new Book("book1", 10000, 10, "ho", "hi");
        em.persist(book1);
        int count = 5;
        Long orderId = orderService.order(member1.getId(), book1.getId(), count);
        Order order = orderRepository.findById(orderId).get();
        assertThat(order.getOrderItems().size()).isEqualTo(1);
        



    }

}