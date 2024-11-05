package Step1.change.domain;

import Step1.change.dto.member.UpdateMemberRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(String name) {
        this.name = name;
        this.address = null;
    }

    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }
    public void changeOrder(Order order){
        orders.add(order);
    }

    public void updateName(String name) {
        this.name = name;
    }
}
