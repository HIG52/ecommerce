package kr.hhplus.be.server.api.balance.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.entity.AuditingFields;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_balance_history")
public class UserBalanceHistory extends AuditingFields {

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

}
