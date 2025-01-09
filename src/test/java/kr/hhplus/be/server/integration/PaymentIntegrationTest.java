package kr.hhplus.be.server.integration;

import kr.hhplus.be.server.api.payment.presentation.controller.PaymentController;
import kr.hhplus.be.server.api.payment.presentation.dto.PaymentRequestDTO;
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
public class PaymentIntegrationTest {

    @Autowired
    private PaymentController paymentController;

    @Test
    void 여러_유저_동시_결제_테스트() throws InterruptedException {
        // given
        long orderId = 1L; // paymentData.sql에서 삽입된 주문 ID
        long couponId = 1L; // paymentData.sql에서 삽입된 쿠폰 ID
        int threadCount = 40; // 동시 요청 수

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            long userId = i + 1; // 각 요청마다 다른 사용자 ID 설정
            executorService.submit(() -> {
                try {
                    // 결제 요청 데이터
                    PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(
                            userId,
                            orderId,
                            10000L, // 결제 금액
                            couponId
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
        assertThat(successCount.get()).isLessThanOrEqualTo(10); // 성공한 요청 수 (예: 잔액 제한에 따라)
        assertThat(failCount.get()).isGreaterThanOrEqualTo(30); // 실패한 요청 수
    }

}
