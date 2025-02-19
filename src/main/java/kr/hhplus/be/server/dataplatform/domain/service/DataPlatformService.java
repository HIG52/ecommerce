package kr.hhplus.be.server.dataplatform.domain.service;

import kr.hhplus.be.server.balance.domain.service.info.BalanceInfo;
import kr.hhplus.be.server.dataplatform.domain.DataPlatFormRepository;
import kr.hhplus.be.server.dataplatform.domain.entity.DataPlatFormEvent;
import kr.hhplus.be.server.payment.presentation.dto.PaymentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPlatformService {

    private final DataPlatFormRepository dataPlatFormRepository;

    @Autowired
    public DataPlatformService(DataPlatFormRepository dataPlatFormRepository) {
        this.dataPlatFormRepository = dataPlatFormRepository;
    }

    public boolean sendData(BalanceInfo balanceInfo) {
        //TODO : 데이터플랫폼으로 데이터 전송
        return true;
    }

    public void createDataFlatFormEvent(PaymentRequestDTO paymentRequestDTO) {
        DataPlatFormEvent dataPlatFormEvent = DataPlatFormEvent.createDataPlatFormEvent(paymentRequestDTO);
        dataPlatFormRepository.save(dataPlatFormEvent);
    }
}
