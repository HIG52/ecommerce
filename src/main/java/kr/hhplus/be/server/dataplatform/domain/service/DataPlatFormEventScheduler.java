package kr.hhplus.be.server.dataplatform.domain.service;

import kr.hhplus.be.server.dataplatform.domain.DataPlatFormRepository;
import kr.hhplus.be.server.dataplatform.domain.entity.DataPlatFormEvent;
import kr.hhplus.be.server.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DataPlatFormEventScheduler {

    private final DataPlatFormRepository dataPlatFormRepository;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public DataPlatFormEventScheduler(DataPlatFormRepository dataPlatFormRepository, KafkaProducer kafkaProducer) {
        this.dataPlatFormRepository = dataPlatFormRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Scheduled(fixedRate = 5000) // 5초마다 실행 예시
    public void processOutboxEvents() {
        List<DataPlatFormEvent> beforeEvents = dataPlatFormRepository.findByStatus("before");
        for (DataPlatFormEvent event : beforeEvents) {
            try {
                // Kafka 전송
                kafkaProducer.sendMessage("my-topic", event.getPayload());

                // 전송 성공 -> 상태 업데이트
                event.updateStatus("after");

                dataPlatFormRepository.save(event);

            } catch (Exception e) {
                log.error("Failed to send event to Kafka. eventId={}", event.getId(), e);
            }
        }
    }
}
