# 쿠폰 발급 시스템 설계

## 1. 쿠폰 재고 관리

### 저장 방식
- Redis의 키를 사용하여 쿠폰의 남은 수량을 관리한다.
- **예시**: `coupon_inventory:{couponId}`

### 재고 감소 처리
- 쿠폰 발급 요청 시, Redis의 원자적 연산인 `DECR`을 사용하여 재고를 감소한다.
- 원자적 연산 덕분에 **고동시성 상황에서도 안전**하게 재고가 관리된다.

## 2. 쿠폰 수령자 기록

### 기록 방식
- 쿠폰 발급이 성공하면, Redis의 `Set` 또는 `Sorted Set`을 이용하여 사용자 ID를 기록한다.
- **예시**: 키 `coupon_issued:{couponId}` 에 사용자 ID 추가

## 3. 최종 발급 기록 (영속성 보장)

### 영속적 저장
- 실제 **DB 테이블(`user_coupon`)에 발급받은 사용자와 쿠폰의 관계를 저장**한다.
- 이를 통해 **Redis 장애 시에도 DB 기록을 바탕으로 재계산 및 복구**할 수 있게된다.

## 4. 동시성 및 확장성 고려

### 동시성 보장
- Redis의 단일 키 연산(`DECR`, `INCR`)은 **원자적**이므로, 선착순 쿠폰 발급과 같은 **동시성 상황에서도 안전**하게 처리할 수 있다.

### 시스템 안정성
- **발급 기록은 먼저 Redis에 저장되고, 이후에 DB와 동기화**된다.
- Redis 장애가 발생하더라도, **DB에 저장된 영속적 기록을 기반으로 복구**할 수 있다.
