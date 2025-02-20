package kr.hhplus.be.server.dataplatform.presentation.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.dataplatform.domain.service.DataPlatformService;
import kr.hhplus.be.server.dataplatform.presentation.event.DataTransmissionEvent;
import kr.hhplus.be.server.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DataPlatformEventListener {

    private final DataPlatformService dataPlatformService;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleDataTransmissionEvent(DataTransmissionEvent event) {
        dataPlatformService.createDataFlatFormEvent(event.getPaymentRequestDTO());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDataTransmissionEventAfterCommit(DataTransmissionEvent event) {
        try {
            // PaymentRequestDTO 객체를 JSON 문자열로 변환
            String json = objectMapper.writeValueAsString(event.getPaymentRequestDTO());
            kafkaProducer.sendMessage("test-topic", json);
        } catch (JsonProcessingException e) {
            // 변환 실패 시 적절한 예외 처리를 진행
            e.printStackTrace();
        }
    }

}
