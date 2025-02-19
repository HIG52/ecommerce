package kr.hhplus.be.server.dataplatform.presentation.listener;

import kr.hhplus.be.server.dataplatform.domain.service.DataPlatformService;
import kr.hhplus.be.server.dataplatform.presentation.event.DataTransmissionEvent;
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

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleDataTransmissionEvent(DataTransmissionEvent event) {
        dataPlatformService.createDataFlatFormEvent(event.getPaymentRequestDTO());
    }

}
