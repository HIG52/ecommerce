package kr.hhplus.be.server.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.dataplatform.domain.service.DataPlatformService;
import kr.hhplus.be.server.payment.presentation.dto.PaymentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final DataPlatformService dataPlatformService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consume(String message) {
        try {
            // JSON 문자열을 PaymentRequestDTO 객체로 변환
            PaymentRequestDTO dto = objectMapper.readValue(message, PaymentRequestDTO.class);
            // DTO에서 id 값 추출 (예: orderId)
            Long orderId = dto.orderId();

            // 추출한 id 값을 기반으로 아웃박스 테이블 상태 업데이트 수행
            dataPlatformService.updateOutboxStatus(orderId);
        } catch (Exception e) {
            // JSON 변환 실패 혹은 업데이트 실패 시 적절한 예외 처리
            e.printStackTrace();
        }
    }

}
