package kr.hhplus.be.server.balance.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("정상적으로 User 객체를 생성한다.")
    void createUser() {
        // given
        String userName = "testUser";
        Long initialBalance = 1000L;

        // when
        User user = User.createUser(userName, initialBalance);

        // then
        assertThat(user.getUserName()).isEqualTo(userName);
        assertThat(user.getBalance()).isEqualTo(initialBalance);
    }

    @Test
    @DisplayName("잔액을 추가하면 잔액이 증가한다.")
    void addBalance() {
        // given
        User user = User.createUser("testUser", 1000L);

        // when
        user.addBalance(500L);

        // then
        assertThat(user.getBalance()).isEqualTo(1500L);
    }

    @Test
    @DisplayName("잔액을 차감하면 잔액이 감소한다.")
    void decreaseBalance() {
        // given
        User user = User.createUser("testUser", 1000L);

        // when
        user.decreaseBalance(300L);

        // then
        assertThat(user.getBalance()).isEqualTo(700L);
    }

    @Test
    @DisplayName("잔액을 차감하여 음수 잔액이 되도록 하면 잔액이 음수가 된다.")
    void decreaseBalanceNegative() {
        // given
        User user = User.createUser("testUser", 1000L);

        // when
        user.decreaseBalance(1500L);

        // then
        assertThat(user.getBalance()).isEqualTo(-500L);
    }

}