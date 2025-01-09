package kr.hhplus.be.server.dataflatform;

import kr.hhplus.be.server.api.balance.domain.service.response.BalanceResponse;
import org.springframework.stereotype.Service;

@Service
public class DataPlatformService {

    public boolean sendData(BalanceResponse balanceResponse) {
        //TODO : 데이터플랫폼으로 데이터 전송
        return true;
    }
}
