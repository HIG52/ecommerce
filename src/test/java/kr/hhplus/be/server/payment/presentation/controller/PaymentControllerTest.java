package kr.hhplus.be.server.payment.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.payment.presentation.dto.PaymentRequestDTO;
import kr.hhplus.be.server.payment.presentation.usecase.PaymentUsecase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/paymentData.sql")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(topics = {"test-topic"})
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentUsecase paymentUsecase;

    // Kafka 메시지를 수신하기 위한 BlockingQueue
    private static final LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    // Kafka에서 "test-topic"으로 발행된 메시지를 수신하는 리스너
    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(String message) {
        messageQueue.offer(message);
    }

    @Test
    @DisplayName("POST /api/payments/ 요청시 결제를 생성하고 Kafka 메시지가 전송된다.")
    void payments_withKafkaMessage() throws Exception {
        // given: 요청 DTO 준비
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(1L, 1L, 1L, 5000L);

        // when: 결제 API 호출 (이 과정에서 이벤트 발행 후 Kafka 메시지 전송)
        mockMvc.perform(post("/api/payments/")
                        .content(objectMapper.writeValueAsString(paymentRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(1L));

        // then: Kafka 메시지 수신 검증 (최대 10초 대기)
        String receivedMessage = messageQueue.poll(10, TimeUnit.SECONDS);
        Assertions.assertNotNull(receivedMessage, "Kafka 메시지를 5초 내에 수신하지 못했습니다.");

        // JSON 메시지를 PaymentRequestDTO로 변환 후, orderId 비교
        PaymentRequestDTO receivedDTO = objectMapper.readValue(receivedMessage, PaymentRequestDTO.class);
        Assertions.assertEquals(paymentRequestDTO.orderId(), receivedDTO.orderId(), "orderId가 일치하지 않습니다.");
    }

}