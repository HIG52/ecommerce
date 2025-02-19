package kr.hhplus.be.server.dataplatform.domain.service;

import kr.hhplus.be.server.dataplatform.domain.DataPlatFormRepository;
import kr.hhplus.be.server.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DataPlatFormEventScheduler {

    @Autowired
    private DataPlatFormRepository dataPlatFormRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Scheduled(fixedRate = 5000) // 5초마다 실행 예시
    public void processOutboxEvents() {
        /*List<OutboxEvent> pendingEvents = outboxRepository.findTop100ByStatus("PENDING");
        for (OutboxEvent event : pendingEvents) {
            try {
                // Kafka 전송
                kafkaProducer.sendMessage("my-topic", event.getPayload());

                // 전송 성공 -> 상태 업데이트
                event.setStatus("SENT");
                outboxRepository.save(event);
            } catch (Exception e) {
                // 전송 실패 -> 재시도 로직, 로그 적재...
                log.error("Failed to send event to Kafka. eventId={}", event.getId(), e);
                // 필요하다면 fail count, retry interval 등을 관리
            }
        }*/
    }
}
