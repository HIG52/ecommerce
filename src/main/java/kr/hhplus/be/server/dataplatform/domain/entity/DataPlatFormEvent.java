package kr.hhplus.be.server.dataplatform.domain.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.payment.presentation.dto.PaymentRequestDTO;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "data_flat_form_event")
public class DataPlatFormEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aggregate_id", nullable = false)
    private Long aggregateId;

    @Column(name = "event_type", nullable = false, length = 255)
    private String eventType;

    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // 엔티티 저장 전, 생성 및 수정 시간 자동 설정
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    protected DataPlatFormEvent() {
    }

    private DataPlatFormEvent(Long aggregateId, String eventType, String payload, String status) {
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = status;
    }

    public static DataPlatFormEvent createDataPlatFormEvent(PaymentRequestDTO paymentRequestDTO) {
        return new DataPlatFormEvent(
                paymentRequestDTO.orderId(), "payment_complate", paymentRequestDTO.toString(), "before"
        );
    }

    public void updateStatus(String status){
        this.status = status;
    }
}
