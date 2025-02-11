package kr.hhplus.be.server.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderInsert {
    private static final String URL = "jdbc:mysql://localhost:3306/hhplus?useSSL=false&serverTimezone=UTC";
    private static final String USER = "application";
    private static final String PASSWORD = "application";

    private static final int TOTAL_RECORDS = 1_000_000; // 총 100만 건
    private static final int BATCH_SIZE = 10_000; // 배치 크기
    private static final int THREAD_COUNT = 10; // 병렬 스레드 개수

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int startId = i * (TOTAL_RECORDS / THREAD_COUNT) + 1;
            final int endId = (i + 1) * (TOTAL_RECORDS / THREAD_COUNT);
            executor.submit(() -> insertOrdersBatch(startId, endId));
        }
        executor.shutdown();
    }

    private static void insertOrdersBatch(int startId, int endId) {
        String insertOrderSQL = "INSERT INTO `order` (order_id, created_at, modify_at, order_total_amount, user_id, payment_status, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        String insertOrderDetailSQL = "INSERT INTO order_detail (order_detail_id, order_id, order_quantity, created_at, modify_at, order_amount, product_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Random random = new Random();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);

            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL);
                 PreparedStatement orderDetailStmt = conn.prepareStatement(insertOrderDetailSQL)) {

                for (int i = startId; i <= endId; i++) {
                    // 주문 정보 생성
                    int userId = random.nextInt(100) + 1;  // 1~100 사이 랜덤 사용자
                    int productId = 101 + (i % 20); // 101~120 사이 상품
                    int quantity = (i % 5) + 1;  // 1~5 랜덤 수량
                    int productPrice = (500 + (productId - 101) * 100); // 상품 가격 (500 ~ 2400)
                    int totalAmount = productPrice * quantity;

                    String paymentStatus = switch (i % 3) {
                        case 0 -> "SUCCESS";
                        case 1 -> "PENDING";
                        default -> "FAIL";
                    };
                    String status = switch (i % 3) {
                        case 0 -> "PAYMENT_COMPLETED";
                        case 1 -> "ORDERED";
                        default -> "CANCELLED";
                    };

                    // created_at 및 modify_at 랜덤 생성
                    LocalDateTime createdAt;
                    if (random.nextDouble() < 0.3) {
                        // 최근 3일 내 30% 확률
                        createdAt = LocalDateTime.now().minusDays(random.nextInt(3));
                    } else {
                        // 최근 3일 이후 랜덤 날짜 (예: 최근 3일 + 0~30일 랜덤)
                        createdAt = LocalDateTime.now().minusDays(3 + random.nextInt(30));
                    }

                    // modify_at은 created_at 이후 0~5일 내 랜덤 설정
                    LocalDateTime modifyAt = createdAt.plusDays(random.nextInt(6));

                    // 주문 데이터 추가
                    orderStmt.setInt(1, i);
                    orderStmt.setString(2, createdAt.format(dateTimeFormatter));
                    orderStmt.setString(3, modifyAt.format(dateTimeFormatter));
                    orderStmt.setInt(4, totalAmount);
                    orderStmt.setInt(5, userId);
                    orderStmt.setString(6, paymentStatus);
                    orderStmt.setString(7, status);
                    orderStmt.addBatch();

                    // 주문 상세 데이터 추가
                    orderDetailStmt.setInt(1, i);
                    orderDetailStmt.setInt(2, i);
                    orderDetailStmt.setInt(3, quantity);
                    orderDetailStmt.setString(4, createdAt.format(dateTimeFormatter));
                    orderDetailStmt.setString(5, modifyAt.format(dateTimeFormatter));
                    orderDetailStmt.setInt(6, totalAmount);
                    orderDetailStmt.setInt(7, productId);
                    orderDetailStmt.addBatch();

                    // 배치 실행 및 커밋
                    if (i % BATCH_SIZE == 0) {
                        orderStmt.executeBatch();
                        orderDetailStmt.executeBatch();
                        conn.commit();
                        System.out.println("Committed batch at ID: " + i);
                    }
                }

                // 남은 데이터 커밋
                orderStmt.executeBatch();
                orderDetailStmt.executeBatch();
                conn.commit();
                System.out.println("Finished inserting from " + startId + " to " + endId);

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}