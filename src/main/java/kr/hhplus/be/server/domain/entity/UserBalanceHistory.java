package kr.hhplus.be.server.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_balance_history")
public class UserBalanceHistory {

    @Id
    @Column(name = "user_balance_history_id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "history_type")
    private String historyType;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "inserted_at")
    private LocalDateTime insertedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modify_at")
    private LocalDateTime modifyAt;

}
