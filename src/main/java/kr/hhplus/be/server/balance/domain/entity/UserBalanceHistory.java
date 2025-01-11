package kr.hhplus.be.server.balance.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.type.HistoryType;
import kr.hhplus.be.server.common.entity.AuditingFields;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user_balance_history")
public class UserBalanceHistory extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_balance_history_id", nullable = false)
    private Long userBalanceHistoryId;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "history_type")
    private HistoryType historyType;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "inserted_at")
    private LocalDateTime insertedAt;

    protected UserBalanceHistory() {
    }

    private UserBalanceHistory(Long userId, HistoryType historyType, Long amount, LocalDateTime insertedAt) {
        this.userId = userId;
        this.historyType = historyType;
        this.amount = amount;
        this.insertedAt = insertedAt;
    }

    public static UserBalanceHistory createUserBalanceHistory(Long userId, HistoryType historyType, Long amount) {
        LocalDateTime now = LocalDateTime.now();
        return new UserBalanceHistory(userId, historyType, amount, now);
    }
}
