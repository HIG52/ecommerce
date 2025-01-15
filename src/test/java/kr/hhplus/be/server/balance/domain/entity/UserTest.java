package kr.hhplus.be.server.balance.domain.entity;

import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.createUser("testUser", 1000L);
        ReflectionTestUtils.setField(user, "userId", 1L);
    }

    @Test
    @DisplayName("잔액을 추가하면 정상적으로 잔액이 증가한다")
    void addBalance_Success() {
        // given
        long amountToAdd = 500L;

        // when
        user.addBalance(amountToAdd);

        // then
        assertThat(user.getBalance()).isEqualTo(1500L);
    }

    @Test
    @DisplayName("잔액을 차감하면 정상적으로 잔액이 감소한다")
    void decreaseBalance_Success() {
        // given
        long amountToDecrease = 500L;

        // when
        user.decreaseBalance(amountToDecrease);

        // then
        assertThat(user.getBalance()).isEqualTo(500L);
    }

    @Test
    @DisplayName("잔액이 부족한 경우 decreaseBalance 호출 시 CustomExceptionHandler를 반환한다")
    void decreaseBalance_InsufficientBalance() {
        // given
        long amountToDecrease = 2000L;

        // when & then
        assertThatThrownBy(() -> user.decreaseBalance(amountToDecrease))
                .isInstanceOf(CustomExceptionHandler.class)
                .hasMessage(ErrorCode.BALANCE_IS_NOT_ENOUGH.getMessage());
    }

    @Test
    @DisplayName("사용자를 생성하면 초기 잔액과 사용자 이름이 올바르게 설정된다")
    void createUser_Success() {
        // given
        String expectedName = "testUser";
        long expectedBalance = 1000L;

        // then
        assertThat(user.getUserName()).isEqualTo(expectedName);
        assertThat(user.getBalance()).isEqualTo(expectedBalance);
    }

}