package kr.hhplus.be.server.coupon.presentation.controller;

import kr.hhplus.be.server.coupon.presentation.dto.CouponRequestDTO;
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

@Sql("/couponData.sql")
@SpringBootTest
@ActiveProfiles("test")
public class CouponIntegrationTest {

    @Autowired
    private CouponController couponController;

    @Test
    void 여러_유저_동시_쿠폰다운로드_테스트() throws InterruptedException {
        // given
        long couponId = 1L; // couponData.sql에서 삽입된 쿠폰 ID
        int threadCount = 40; // 동시 요청 수

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            long userId = i + 1; // 각 요청마다 다른 사용자 ID 설정
            executorService.submit(() -> {
                try {
                    CouponRequestDTO couponRequestDTO = new CouponRequestDTO(userId, couponId);
                    couponController.couponDownload(couponRequestDTO);
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

        // 검증: 재고가 10개라면 성공은 10번 이하여야 함
        assertThat(successCount.get()).isEqualTo(30); // 성공한 요청 수
        assertThat(failCount.get()).isEqualTo(10); // 실패한 요청 수

    }

}
