package Step1.change.Repository;


import Step1.change.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"member", "delivery"})
    @Query("select o from Order o")
    List<Order> findAllWithMD();


    @EntityGraph(attributePaths = {"member","delivery", "orderItem", "orderItem.item"})
    @Query("select distinct o from Order o")
    //만약 orderItem과 Order관계가 다대일일 경우 distinct를 사용해야되지 않나요?
    List<Order>findAllWithMDI();

    @EntityGraph(attributePaths = {"member", "delivery"})
    @Query("select o from Order o")
    //이 경우 member, delivery만 가져오고 paging 시 batchsize를 이용해야되기에 OrderItem은 추후 가져오는게 맞나요?
    Page<Order>findAllWithMDI_Paging(Pageable pageable);




}
