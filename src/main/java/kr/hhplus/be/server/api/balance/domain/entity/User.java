package kr.hhplus.be.server.api.balance.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.entity.AuditingFields;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "balance")
    private Long balance;

    protected User() {

    }

    private User(String userName, Long balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public static User createUser(String userName, Long balance) {
        return new User(userName, balance);
    }

    public void addBalance(long amount) {
        this.balance += amount;
    }

    public void decreaseBalance(long amount) {
        this.balance -= amount;
    }
}
