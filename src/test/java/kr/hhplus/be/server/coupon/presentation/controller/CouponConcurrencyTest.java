package kr.hhplus.be.server.coupon.presentation.controller;

import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.presentation.dto.CouponRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/couponData.sql")
@SpringBootTest
@ActiveProfiles({"test"})
public class CouponConcurrencyTest {

    @Autowired
    private CouponController couponController;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName("여러유저가 동시에 쿠폰을 발급할때 재고의 갯수만큼만 발급된다.")
    void couponDownloadTest() throws InterruptedException {
        // given
        long couponId = 1L; // couponData.sql에서 삽입된 쿠폰 ID
        int threadCount = 10; // 동시 요청 수
        int initialInventory = 5; // 미리 적재할 쿠폰 재고

        // 쿠폰 재고를 Redis에 미리 적재
        String inventoryKey = "coupon_inventory:" + couponId;
        String issuedKey = "coupon_issued:" + couponId;
        redisTemplate.opsForValue().set(inventoryKey, String.valueOf(initialInventory));
        // 이전 발급 내역이 남아있을 수 있으므로 삭제 (없으면 무시됨)
        redisTemplate.delete(issuedKey);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            long userId = i+1; // 각 요청마다 다른 사용자 ID 설정
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

        // 검증: 재고가 5개
        assertThat(couponRepository.getUserCouponCount(couponId)).isEqualTo(5);
        assertThat(successCount.get()).isEqualTo(5); // 성공한 요청 수
        assertThat(failCount.get()).isEqualTo(5); // 실패한 요청 수

    }

}
