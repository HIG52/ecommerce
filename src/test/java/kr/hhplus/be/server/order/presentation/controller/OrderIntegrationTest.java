package kr.hhplus.be.server.order.presentation.controller;

import kr.hhplus.be.server.order.presentation.controller.OrderController;
import kr.hhplus.be.server.order.presentation.dto.OrderRequestDTO;
import kr.hhplus.be.server.order.presentation.dto.OrderResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/orderData.sql")
@SpringBootTest
@ActiveProfiles("test")
public class OrderIntegrationTest {

    @Autowired
    private OrderController orderController;

    @Test
    void 여러_유저_동시_주문_생성_테스트() throws InterruptedException {
        // given
        long productId1 = 1L; // orderData.sql에서 삽입된 상품 ID
        long productId2 = 2L;
        int threadCount = 10; // 동시 요청 수

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            long userId = i + 1; // 각 요청마다 다른 사용자 ID 설정
            executorService.submit(() -> {
                try {
                    // 주문 요청 데이터
                    OrderRequestDTO orderRequestDTO = new OrderRequestDTO(
                            userId,
                            10000L, // 주문 총 금액
                            List.of(productId1, productId2),
                            List.of(2, 1), // 각 상품의 주문 수량
                            List.of(1200000L, 800000L) // 각 상품의 가격
                    );

                    OrderResponseDTO response = orderController.createOrders(orderRequestDTO).getBody();
                    System.out.println("주문 성공: " + response.orderId());
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
        //1번 상품의 재고가 10개라 5번 성공후 이후는 실패
        assertThat(successCount.get()).isEqualTo(5); // 성공한 요청 수
        assertThat(failCount.get()).isEqualTo(5); // 실패한 요청 수
    }

}
