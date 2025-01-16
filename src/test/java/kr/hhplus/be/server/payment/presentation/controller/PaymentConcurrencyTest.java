package kr.hhplus.be.server.payment.presentation.controller;

import kr.hhplus.be.server.payment.presentation.dto.PaymentRequestDTO;
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

@Sql("/paymentData.sql")
@SpringBootTest
@ActiveProfiles("test")
public class PaymentConcurrencyTest {

    @Autowired
    private PaymentController paymentController;

    @Test
    @DisplayName("한유저가 중복 결제를 시도할경우 잔액이 한번만 차감이 됨")
    void paymentConcurrencyTest() throws InterruptedException {
        // given
        long userId = 1L; // paymentData.sql에서 삽입된 유저 ID
        long orderId = 1L; // paymentData.sql에서 삽입된 주문 ID
        long couponId = 1L; // paymentData.sql에서 삽입된 쿠폰 ID
        int threadCount = 10; // 동시 요청 수

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    // 결제 요청 데이터
                    PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(
                            userId,
                            orderId,
                            couponId,
                            5000L

                    );

                    paymentController.payments(paymentRequestDTO);
                    successCount.incrementAndGet();
                } catch (Exception ignored) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 작업을 완료할 때까지 대기
        executorService.shutdown();

        // then
        System.out.println("성공 횟수: " + successCount);
        System.out.println("실패 횟수: " + failCount);

        // 재고나 금액에 따라 성공/실패 횟수 검증
        assertThat(successCount.get()).isEqualTo(1); // 성공한 요청 수 (예: 잔액 제한에 따라)
        assertThat(failCount.get()).isEqualTo(threadCount-1); // 실패한 요청 수
    }

}
