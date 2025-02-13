package kr.hhplus.be.server.dataflatform.presentation.listener;

import kr.hhplus.be.server.dataflatform.DataPlatformService;
import kr.hhplus.be.server.dataflatform.presentation.event.DataTransmissionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataPlatformEventListener {

    private final DataPlatformService dataPlatformService;

    @Async  // 별도의 쓰레드에서 비동기로 실행
    @Transactional(rollbackFor = Exception.class)  // 예외 발생 시 이 도메인의 트랜잭션만 롤백
    @EventListener
    public void handleDataTransmissionEvent(DataTransmissionEvent event) {
        var balanceInfo = event.getBalanceInfo();

        // 데이터 전송 수행
        boolean isSent = dataPlatformService.sendData(balanceInfo);
        /*if (!isSent) { TODO : 추후 트랜잭션 롤백 로직 추가 예정
            // 전송 실패 시 예외를 던져 트랜잭션 롤백 (보상 로직 추가 가능)
            throw new IllegalStateException("데이터 전송 실패");
        }*/

        System.out.println("데이터 전송 성공: " + balanceInfo);
    }

}
