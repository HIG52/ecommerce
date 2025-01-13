package kr.hhplus.be.server.dataflatform;

import kr.hhplus.be.server.balance.domain.service.response.BalanceInfo;
import org.springframework.stereotype.Service;

@Service
public class DataPlatformService {

    public boolean sendData(BalanceInfo balanceInfo) {
        //TODO : 데이터플랫폼으로 데이터 전송
        return true;
    }
}
