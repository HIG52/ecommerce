package kr.hhplus.be.server.balance.domain.entity;

import kr.hhplus.be.server.common.type.HistoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserBalanceHistoryTest {

    @Test
    @DisplayName("정상적으로 UserBalanceHistory 객체를 생성한다.")
    void createUserBalanceHistory() {
        // given
        Long userId = 1L;
        HistoryType historyType = HistoryType.CHARGE;
        Long amount = 1000L;

        // when
        UserBalanceHistory history = UserBalanceHistory.createUserBalanceHistory(userId, historyType, amount);

        // then
        assertThat(history.getUserId()).isEqualTo(userId);
        assertThat(history.getHistoryType()).isEqualTo(historyType);
        assertThat(history.getAmount()).isEqualTo(amount);
        assertThat(history.getInsertedAt()).isNotNull();
        assertThat(history.getInsertedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

}