package kr.hhplus.be.server.coupon.domain.service;

import jakarta.annotation.PostConstruct;
import kr.hhplus.be.server.common.error.CustomExceptionHandler;
import kr.hhplus.be.server.common.error.ErrorCode;
import kr.hhplus.be.server.coupon.domain.repository.CouponRepository;
import kr.hhplus.be.server.coupon.presentation.dto.CouponIssuedRequestDTO;
import kr.hhplus.be.server.coupon.presentation.dto.UserCouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CouponInventoryService {

    private final RedisTemplate<String, String> redisTemplate;
    private final CouponRepository couponRepository;

    private DefaultRedisScript<Long> decrementScript;

    @Autowired
    public CouponInventoryService(RedisTemplate<String, String> redisTemplate, CouponRepository couponRepository) {

        this.redisTemplate = redisTemplate;
        this.couponRepository = couponRepository;
    }

    @PostConstruct
    public void init() {

        decrementScript = new DefaultRedisScript<>();

        String script = "local current = redis.call('GET', KEYS[1]) " +
                "if not current then return -1 end " +
                "current = tonumber(current) " +
                "if not current then return -2 end " +
                "if current > 0 then " +
                "   redis.call('DECR', KEYS[1]) " +
                "   return 1 " +
                "else " +
                "   return 0 " +
                "end";

        decrementScript.setScriptText(script);
        decrementScript.setResultType(Long.class);
    }

    public boolean processCouponDownload(long couponId, long userId) {

        String issuedKey = "coupon_issued:" + couponId;
        String inventoryKey = "coupon_inventory:" + couponId;
        String strUserId = String.valueOf(userId);

        // 이미 발급받은 사용자인지 확인 (set에 사용자 ID가 존재하면 이미 발급된 것)
        Boolean isMember = redisTemplate.opsForSet().isMember(issuedKey, strUserId);
        if (Boolean.TRUE.equals(isMember)) {
            // 커스텀 예외를 던지거나, 원하는 방식으로 예외 처리
            throw new CustomExceptionHandler(ErrorCode.COUPON_ALREADY_DOWNLOAD);
        }

        // 재고 감소를 위한 Lua 스크립트 실행
        Long result = redisTemplate.execute(decrementScript, Collections.singletonList(inventoryKey));

        // 원자적 연산 결과가 1이면 재고 감소 성공
        if (result != null && result == 1) {
            // 발급 성공 시, 발급받은 사용자 정보를 Redis에 기록
            redisTemplate.opsForSet().add(issuedKey, strUserId);
            return true;
        }
        return false;
    }

    public boolean issuedCouponQuantity(CouponIssuedRequestDTO couponIssuedRequestDTO) {
        // 1. CouponRequestDTO에서 쿠폰 ID와 초기 재고 수량 추출
        long couponId = couponIssuedRequestDTO.couponId();
        int quantity = couponIssuedRequestDTO.quantity(); // 쿠폰 재고 수량
        int userCouponCount = couponRepository.getUserCouponCount(couponId);

        int resultQuantity = quantity - userCouponCount;

        if(resultQuantity <= 0) {
            throw new CustomExceptionHandler(ErrorCode.COUPON_NOT_ISSUED);
        }
        // 2. Redis에서 사용할 key 이름 설정
        String inventoryKey = "coupon_inventory:" + couponId;

        // 3. 쿠폰 재고를 Redis에 저장 (문자열로 저장)
        redisTemplate.opsForValue().set(inventoryKey, String.valueOf(quantity));

        return true;
    }
}
