package kr.hhplus.be.server.dataplatform.presentation.event;

import kr.hhplus.be.server.balance.domain.service.info.BalanceInfo;
import kr.hhplus.be.server.payment.presentation.dto.PaymentRequestDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DataTransmissionEvent extends ApplicationEvent {

    private final PaymentRequestDTO paymentRequestDTO;

    public DataTransmissionEvent(Object source, PaymentRequestDTO paymentRequestDTO) {
        super(source);
        this.paymentRequestDTO = paymentRequestDTO;
    }

}
