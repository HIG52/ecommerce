package kr.hhplus.be.server.balance.presentation.controller;

import kr.hhplus.be.server.balance.domain.entity.User;
import kr.hhplus.be.server.balance.infrastructure.repositoryImpl.BalanceRepositoryImpl;
import kr.hhplus.be.server.balance.presentation.dto.BalanceChargeRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/userData.sql")
@SpringBootTest
@ActiveProfiles("test")
public class BalanceChargeConcurrencyTest {

    @Autowired
    private BalanceController balanceController;
    @Autowired
    private BalanceRepositoryImpl balanceRepositoryImpl;

    @Test
    @DisplayName("한명의 유저가 동시에 여러번 충전시 전부 성공")
    void balanceChargeTest() throws InterruptedException {

        // given
        long userId = 6L;
        long amount = 1000L;
        int threadCount = 10; // 동시 요청 수

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    BalanceChargeRequestDTO balanceChargeRequestDTO = new BalanceChargeRequestDTO(amount);
                    balanceController.userPointCharge(userId, balanceChargeRequestDTO);
                    successCount.incrementAndGet();
                } catch (Exception ignored) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 쓰레드가 작업을 완료할 때까지 대기
        executorService.shutdown();

        // then
        System.out.println("성공 횟수: " + successCount);
        System.out.println("실패 횟수: " + failCount);

        User result = balanceRepositoryImpl.getUser(userId);
        System.out.println("result.getBalance(): " + result.getBalance());
        assertThat(result.getBalance()).isEqualTo(12000L);
        assertThat(successCount.get()).isEqualTo(10); // 성공한 요청 수
        assertThat(failCount.get()).isEqualTo(0); // 실패한 요청 수
    }

}
