package kr.hhplus.be.server.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User extends AuditingFields{

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "balance")
    private Long balance;

    protected User() {

    }

    private User(Long userId, String userName, Long balance) {
        this.userId = userId;
        this.userName = userName;
        this.balance = balance;
    }

    public static User createUser(Long userId, String userName, Long balance) {
        return new User(userId, userName, balance);
    }

    public void addBalance(long amount) {
        this.balance += amount;
    }
}
