package kr.hhplus.be.server.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // 유저
    USER_NOT_FOUND(404, "U001", "사용자 정보가 존재하지 않습니다."),

    // 잔액
    BALANCE_IS_NOT_ENOUGH(400, "B001", "보유 잔액이 부족합니다."),
    BALANCE_CHARGE_FAILED(400, "B002", "충전 결과가 올바르지 않습니다."),
    BALANCE_USE_FAILED(400, "B003", "잔액 차감 결과가 올바르지 않습니다."),
    BALANCE_HISTORY_SAVE_FAILED(400, "B004", "잔액내역 저장 결과가 올바르지 않습니다."),

    // 상품
    PRODUCT_NOT_FOUND(404, "P001", "상품 정보가 존재하지 않습니다."),
    STOCK_QUANTITY_NOT_ENOUGH(400, "P002", "상품의 재고가 존재하지 않습니다."),

    // 쿠폰
    COUPON_NOT_FOUND(404, "C001", "쿠폰 정보가 존재하지 않습니다."),
    COUPON_OUT_OF_STOCK(400, "C002", "쿠폰 재고가 없습니다."),
    COUPON_SAVE_FAILED(400, "C003", "쿠폰 재고가 없습니다."),
    COUPON_ALREADY_DOWNLOAD(400, "C004", "발급된 쿠폰입니다."),
    COUPON_USE_FAILED(400, "C005", "쿠폰 사용 처리에 실패하였습니다."),
    COUPON_IS_EXPIRED(400, "C006", "만료된 쿠폰입니다."),
    COUPON_NOT_ISSUED(400, "C007", "재고가 존재하지 않는 쿠폰입니다."),

    // 주문
    ORDER_NOT_FOUND(404, "O001", "주문을 조회할 수 없습니다."),
    ORDER_NOT_CREATE(404, "O002", "주문 생성에 실패하였습니다."),
    ORDER_STATUS_UPDATE_FAIL(404, "O003", "주문 상태 업데이트에 실패하였습니다."),
    ORDER_PAYMENT_STATUS_UPDATE_FAIL(404, "O004", "주문 결제 상태 업데이트에 실패하였습니다."),
    ORDER_ALREADY_PROCESSED(400, "O005", "이미 처리된 주문입니다."),

    // 결제
    PAYMENT_SAVE_FAILED(400, "PAY001", "결제 정보 저장에 실패하였습니다."),
    PAYMENT_STATUS_UPDATE_FAIL(400, "PAY001", "결제 정보 저장에 실패하였습니다.");

    private final int httpStatus;
    private final String customCode; // 고유 에러 코드
    private final String message;    // 에러 메시지


}
