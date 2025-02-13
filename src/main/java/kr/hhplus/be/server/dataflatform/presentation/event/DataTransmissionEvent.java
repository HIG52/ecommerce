package kr.hhplus.be.server.dataflatform.presentation.event;

import kr.hhplus.be.server.balance.domain.service.info.BalanceInfo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

@Getter
public class DataTransmissionEvent extends ApplicationEvent {

    private final BalanceInfo balanceInfo;

    public DataTransmissionEvent(Object source, BalanceInfo balanceInfo) {
        super(source);
        this.balanceInfo = balanceInfo;
    }
}
