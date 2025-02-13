# MSA 전환을 위한 도메인 분리와 트랜잭션 관리 전략

---

## 목차

1. 서론
    - 개요
    - 서비스 확장의 필요성과 MSA 전환 배경
    - 기존 모놀리식 시스템과의 차이점
   

2. MSA 기반 서비스 분리 전략
   - 도메인별 서비스 분리 기준
   - 서비스 간 통신 방식 (REST, gRPC, 메시지 브로커)
   - 데이터 저장소 분리 및 연관 데이터 처리


3. 트랜잭션 처리의 한계점
   -  분산 트랜잭션과 기존 트랜잭션의 차이
   -  데이터 정합성 문제
   -  네트워크 장애 등 성능 및 지연 시간 이슈

4. 트랜잭션 처리 해결 방안
   - SAGA 패턴
   - Choreography 방식
   - Orchestration 방식
   - 2PC (Two-Phase Commit)와 한계점


5. MSA 환경에서의 트랜잭션 모니터링 및 장애 대응
   - 트랜잭션 모니터링 도구 및 로깅
   - 장애 감지 및 복구 전략
   - 서킷 브레이커 패턴 적용


6. 결론
   - 서비스 분리에 따른 장점과 단점


7. 부록
   - 참고 자료

---

## 서론

### 문서 개요
서비스가 확장됨에 따라 기존의 모놀리식 아키텍처에서 MSA(Micro Service Architecture)로 전환해야하는 필요성이 증가함에 따라
본 문서는 서비스 분리에 따른 트랜잭션의 처리 한계와 해결방안을 중심으로 MSA 전환 시 고려해야 할 사항들을 정리하였다.

### 서비스 확장의 필요성과 MSA 전환 배경
- 대용량 트래픽으로 인한 성능 이슈
- 도메인별 독립적인 배포 및 유지보수의 필요성 증가
- 서비스 확장에 따른 팀의 세분화로 인한 팀간 분업 및 개발 생산성 증가
- 특정 서비스의 개별 확장 필요성

### 기존 모놀리식 시스템과의 차이점

| 항목          | 모놀리식 아키텍처 | 마이크로서비스 아키텍처 |
|--------------|----------------|------------------|
| 서비스 구성  | 하나의 애플리케이션 내에서 모든 기능 제공 | 도메인별로 독립적인 서비스 분리 |
| 데이터 저장소 | 단일 데이터베이스 | 서비스별 개별 데이터베이스 유지 |
| 배포 방식    | 전체 시스템 배포 필요 | 개별 서비스 단위 배포 가능 |
| 장애 영향도  | 하나의 서비스 장애가 전체 시스템에 영향 | 특정 서비스 장애가 전체 시스템에 미치는 영향 최소화 |
| 트랜잭션 관리 | 단일 DB 내에서 ACID 보장 | 분산 트랜잭션 관리 필요 |

---

## MSA 기반 서비스 분리 전략

### 도메인별 서비스 분리 기준

- 도메인 주도 설계(DDD)를 활용하여 비지니스 기능 중심으로 도메인을 분리한다.
- 현재 모놀리식으로 구성된 이커머스에서 예시를 든다면 회원/결제/상품 등을 분리할수 있다.


&rarr; 핵심 기능은 다른 기능들과의 결합도가 높을 가능성이 크기 때문에 의존성 분석 과정이 필요하다.

&rarr; 최대한 의존성을 줄이도록 경계를 지으면서 응집도있고 서비스의 변경에 대한 이유는 하나의 비지니스 영역 때문이도록 하여야 한다.

### 서비스 간 통신 방식

마이크로 서비스들끼리 통신하는 방법에는 크게 동기 방식과 비동기 방식이 존재한다.

- 동기 통신 : 
  - HTTP/REST : 일반적인 통신 방법으로 API를 이용하여 서비스간 HTTP 요청과 응답을 주고 받는다.
  - gRPC : 구글이 개발한 고성능 RPC 프레임워크로, HTTP/2를 사용하여 통신한다.

- 비동기 통신 : 
  - 메시지 큐 : RabbitMQ, Apache Kafka 와 같은 메시지 큐 시스템을 사용하여 서비스 간 비동기적으로 메시지를 전달한다.
  - 이벤트 스트리밍 : Apache Kafka, Amazon Kinesis와 같은 시스템을 사용하여 이벤트 기반으로 데이터 스트림을 처리한다.

*해당 동기, 비동기 통신 외에도 다양한 통신 방법이 존재한다.*

### 데이터 저장소 분리 및 연관 데이터 처리

- 도메인에 맞게 독립된 데이터 저장소를 사용하도록 기존 DB를 분리한다.
- 부득이하게 DB를 합쳐야 하는경우 (예시 : 사용자 데이터 기반 개인화 상품추천 기능 / 구매내역 또는 장바구니 목록이 필요) 
    - MySQL Multi-Source Replication 기능을 사용하여 여러개의 MasterDB 에서 한개의 Slave DB로 동기화 하여 데이터를 통합한다.
    - Replication 방식을 사용한다.

---

## MSA 환경 트랜잭션 처리의 한계점

### 분산 트랜잭션과 기존 트랜잭션의 차이

- 모놀리식 트랜잭션 : 
  - 단일 데이터베이스 내에서 모든 작업이 원자적으로 수행된다.

- 분산 트랜잭션 : 
  - 여러 서비스와 데이터 저장소에 걸쳐 트랜잭션을 수행 해야하므로, 네트워크 지연, 부분실패, 동기화등 추가적인 작업이 필요하다.

### 데이터 정합성 문제

- 각 서비스간 데이터 전파에 지연이 존재하여 **실시간 일관성** 유지가 어려울수도 있다.
- **분산 락, 버전 관리** 등을 통하여 정합성 문제를 해결해야한다.
- 실패시 이전 상태를 복구 하기위한 **보상 트랜잭션** 등의 로직을 구현해야한다.

### 네트워크 장애 등 성능 및 지연 시간 이슈

- 네트워크 통신 장애로 인한 트랜잭션 중지 및 지연이 발생하여 성능에 이슈가 생길수 있음
- 서비스 간에 통신 지연문제에 경우 전체 트랜잭션의 처리시간이 늘고 시스템 응답을 저하시킬수 있다.

---

## 트랜잭션 처리 해결 방안

### SAGA 패턴
- 분산 트랜잭션 환경에서 메시지 또는 이벤트를 주고 받으며 특정 마이크로서비스에서의 작업이 실패하면 이전까지의 작업이 완료된 마이크로서비스들에게
보상 이벤트를 소싱 함으로써 분산환경에서 원자성을 보장해주는 패턴이다.
- 크게 Choreography based SAGA pattern 과 Orchestration based SAGA pattern 으로 나뉜다.

### Choreography based SAGA pattern
- 중앙의 제어 없이 서비스끼리 이벤트로 통신하는 방법
- 서비스들은 특정 동작을 수행하면 도메인 이벤트를 발행하고, 이것을 구독하고 있던 서비스가 그에 따른 트랜잭션을 수행한다.
- 이벤트는 Kafka와 같은 메시지 큐를 이용하여 비동기로 전달한다.
  - 트랜잭션이 실패한다면 보상 이벤트를 발행하여 롤백
  - 메시지를 발행하는 동작도 트랜잭션에 포함 되어야한다.
- 참여자가 적고 중앙제어가 필요 없는경우 적합, 추가 서비스 구현이 필요없고 역할이 분산되어있어 단일 실패지점이 존재하지 않다.
- 명령 추적이 어렵고 순환종속성이 발생할수 있으며 통합테스트가 어렵다는 단점이 있다.

```
// OrderService.java
@Service
public class OrderService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void createOrder(Order order) {
        // 주문 저장 로직 (생략)
        System.out.println("Order created: " + order.getId());

        // 주문 생성 완료 후 이벤트 발행
        eventPublisher.publishEvent(new OrderCreatedEvent(this, order));
    }
}

// OrderCreatedEvent.java
public class OrderCreatedEvent extends ApplicationEvent {
    private final Order order;

    public OrderCreatedEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}

// PaymentService.java
@Component
public class PaymentService {

    // OrderCreatedEvent를 구독하여 처리
    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        Order order = event.getOrder();
        // 결제 처리 로직 (생략)
        System.out.println("Processing payment for order: " + order.getId());
    }
}
```
1. OrderService 는 주문 생성 후 OrderCreatedEvent  이벤트를 발행
2. PaymentService는 해당 이벤트를 구독하여 결제처리를 수행

*중앙 처리 없이 이벤트를 통해 느슨하게 연결 시킨다.*

### Orchestration based SAGA pattern
- 오케스트레이터 라는  중앙 컨트롤러가 보상 작업을 트리거 하는 형식이다.
- 오케스트레이터가 모든 트랜잭션을 처리하고 수행해야 하는 작업을 메시지를 보내 참여자들과 통신한다.
- 작업의 상태를 저장 및 해석하고 있어 분산 트랜잭션의 중앙 집중화가 이루어지고 데이터 일관성을 지킬수 있다.
- 복잡한 워크플로우에 적합하며 활동 흐름의 제어가 가능하다.
- 중앙에서 관리를 위한 복잡한 로직구현이 필요하고 모든 워크플로우를 관리하기 때문에 실패 지점이 될 수 있다.

```
// OrderOrchestrator.java
@Service
public class OrderOrchestrator {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InventoryService inventoryService;

    public void placeOrder(Order order) {
        // 1단계: 주문 생성
        orderService.createOrder(order);

        // 2단계: 결제 처리
        boolean paymentSuccess = paymentService.processPayment(order);
        if (!paymentSuccess) {
            System.out.println("Payment failed for order: " + order.getId());
            orderService.cancelOrder(order);
            return;
        }

        // 3단계: 재고 업데이트
        boolean inventoryUpdated = inventoryService.updateInventory(order);
        if (!inventoryUpdated) {
            System.out.println("Inventory update failed for order: " + order.getId());
            // 보상 트랜잭션: 결제 환불 및 주문 취소
            paymentService.refundPayment(order);
            orderService.cancelOrder(order);
            return;
        }

        System.out.println("Order processed successfully: " + order.getId());
    }
}

// OrderService.java (간단 구현)
@Service
public class OrderService {
    public void createOrder(Order order) {
        System.out.println("Order created: " + order.getId());
        // 주문 저장 로직 (생략)
    }

    public void cancelOrder(Order order) {
        System.out.println("Order canceled: " + order.getId());
        // 주문 취소 로직 (생략)
    }
}

// PaymentService.java (간단 구현)
@Service
public class PaymentService {
    public boolean processPayment(Order order) {
        System.out.println("Processing payment for order: " + order.getId());
        // 결제 처리 로직 (생략)
        return true; // 성공 여부
    }

    public void refundPayment(Order order) {
        System.out.println("Refunding payment for order: " + order.getId());
        // 환불 처리 로직 (생략)
    }
}

// InventoryService.java (간단 구현)
@Service
public class InventoryService {
    public boolean updateInventory(Order order) {
        System.out.println("Updating inventory for order: " + order.getId());
        // 재고 업데이트 로직 (생략)
        return true; // 성공 여부
    }
}
```
1. 중앙의 OrderOrchestrator 가 전체 주문을 제어한다.
2. 단계별로 각 서비스를 호출하고 실패시 보상 로직을 실행시킨다.
*중앙 집권적인 관리*

### 2PC (Two-Phase Commit)와 한계점
- 여러노드에 거ㅓ쳐서 원자성 트랜잭션 커밋을 달성하기 위한 알고리즘
- 단일 노드 트랜잭션에서는 나타나지 않는 새로운 구성 요소인 코디네이터를 사용한다.
- 분산 트랜잭션을 지원하지 않는 nosql은 사용이 불가하다.
- 이종간 DB 에서의 분산 트랜잭션 지원 및 구현이 까다롭다.

---

## MSA 환경에서의 트랜잭션 모니터링 및 장애 대응

### 트랜잭션 모니터링 도구 및 로깅
- Zipkin, Jaeger, OpenTelemetry 등을 활용하여 서비스 간 호출 관계와 트랜잭션 흐름을 실시간으로 추적
- 각 서비스에서 생성되는 trace ID, span ID를 통해 장애 발생 위치 및 원인을 파악
- ELK Stack(Elasticsearch, Logstash, Kibana) 또는 Splunk를 통해 모든 서비스 로그를 수집, 분석

### 장애 감지 및 복구 전략
- 헬스 체크 및 모니터링:
    - 각 서비스의 상태를 주기적으로 확인하는 헬스 체크 API 및 모니터링 에이전트 도입
    - 장애 감지 시 자동으로 알람을 발생시키고, 대시보드를 통해 실시간 모니터링
- 자동 복구 메커니즘:
    - 장애 발생 시, 자동 재시작, 컨테이너 오케스트레이션(Kubernetes 등)을 통한 대체 인스턴스 기동
    - 재시도 로직 및 보상 트랜잭션을 통해 서비스 복구 및 데이터 정합성 확보
- 운영 프로세스:
    - 장애 발생 원인 분석(RCA)을 통해 근본 원인 해결
    - 정기적인 장애 복구 훈련과 시나리오 테스트를 통해, 장애 대응 프로세스의 완성도를 높임


### 서킷 브레이커 패턴 적용
- 원리:
    - 외부 서비스 호출 시, 일정 실패율 이상이 발생하면 호출을 차단하여 전체 시스템에 장애가 전파되지 않도록 함
    - 일정 시간 후 재시도하여 호출을 복구시키는 방식으로, 안정성을 확보
- 적용 효과:
    - 전체 시스템의 장애 전파 방지 및 리소스 낭비 최소화
    - 장애 발생 시, 빠른 실패(fail-fast)와 자동 복구를 통해 안정적인 운영 지원
- 설계 고려사항:
    - 임계값, 타임아웃, 재시도 주기 등 파라미터를 상황에 맞게 최적화
    - 서킷 브레이커 상태(Closed, Open, Half-Open)를 지속적으로 모니터링하여 운영팀에 실시간 알림 제공

---

## 결론

### 서비스 분리에 따른 장점과 단점
- **장점:**
    - **독립 배포 및 확장:** 개별 서비스의 독립적 업데이트와 스케일 아웃 가능
    - **장애 격리:** 특정 서비스 장애가 전체 시스템에 미치는 영향 최소화
    - **팀 분업화:** 도메인별 책임 분리를 통한 개발 생산성 향상
    - **기술 유연성:** 서비스별로 최적의 기술 스택 및 데이터 저장소 선택 가능
- **단점:**
    - **분산 트랜잭션 관리 복잡성:** 여러 서비스 간 데이터 정합성 및 동시성 제어 어려움
    - **추가 통신 비용:** 네트워크 오버헤드 및 메시지 처리 지연
    - **모니터링/운영 비용 증가:** 분산 환경에 따른 중앙 집중 모니터링 및 장애 대응 체계 구축 필요

---
## 부록

### 참고 자료

https://www.atlassian.com/ko/microservices/microservices-architecture/microservices-vs-monolith

https://velog.io/@sorzzzzy/MSA-MSAMicroService-Architecture%EB%9E%80

https://velog.io/@suhongkim98/MSA%EC%99%80-DDD-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4-%EA%B0%84-%ED%86%B5%EC%8B%A0-%EB%B0%A9%EB%B2%95-4-%EC%9E%91%EC%84%B1-%EC%A4%91

https://systorage.tistory.com/entry/%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98MSA%EC%97%90%EC%84%9C-%EB%AA%A8%EB%93%88-%EA%B0%84-%ED%86%B5%EC%8B%A0-%EB%B0%A9%EB%B2%95

https://devocean.sk.com/blog/techBoardDetail.do?ID=166825&boardType=techBlog

https://learn.microsoft.com/ko-kr/azure/architecture/patterns/saga

https://azderica.github.io/01-architecture-msa/

https://tech.kakaopay.com/post/msa-transaction/

https://joobly.tistory.com/69

https://medium.com/@greg.shiny82/%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%94%EB%84%90-%EC%95%84%EC%9B%83%EB%B0%95%EC%8A%A4-%ED%8C%A8%ED%84%B4%EC%9D%98-%EC%8B%A4%EC%A0%9C-%EA%B5%AC%ED%98%84-%EC%82%AC%EB%A1%80-29cm-0f822fc23edb