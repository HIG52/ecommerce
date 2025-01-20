package kr.hhplus.be.server.balance.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.entity.AuditingFields;
import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
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

    @Version
    private Integer version;

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
        if (this.balance < amount) {
            throw new CustomExceptionHandler(ErrorCode.BALANCE_IS_NOT_ENOUGH);
        }
        this.balance -= amount;
    }
}
