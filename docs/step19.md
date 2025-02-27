## 현재 시스템 부하 테스트

### 선착순 쿠폰 발급

- 선정 이유 : 흔히 선착순이라고 하면 많은 유저들이 동시에 접속하여 서버에 부하를 주는 경우가 많고, 이러한 경우 서버에 부하가 많이 가기 때문에 선정 하였음
- 목적 : 선착순 쿠폰 발급 시스템의 부하 테스트를 통해 서버의 부하를 확인하고, 미리 대비할 수 있는 방안을 찾기 위함
- 시나리오 :
  - 선착순 쿠폰 발급 시스템에 대한 부하 테스트를 진행
  - 부하 테스트 결과를 분석하여 서버의 부하를 확인
  - 대비방안 찾기

### 테스트 도구 선정

- artillery
- 선정 이유 : artillery는 Node.js 기반의 오픈소스 부하 테스트 도구로, 쉽게 설치하고 사용할 수 있어 선정하였음
- 목적 : artillery를 사용하여 선착순 쿠폰 발급 시스템에 대한 부하 테스트를 진행, 부하 테스트 결과 분석
- 시나리오 :
  - artillery 설치
  - 선착순 쿠폰 발급 시스템에 대한 부하 테스트를 진행
  - 부하 테스트 결과를 분석
- 참고 : https://artillery.io/
- 테스트 스크립트 : 
```
config:
  target: 'http://localhost:8080'
  phases:
    - duration: 30
      arrivalRate: 10
      name: 'Warm up'
    - duration: 20
      arrivalRate: 20
      rampTo: 200
      name: 'Ramp up'
    - duration: 20
      arrivalRate: 200
      name: 'Sustained max load'
    - duration: 30
      arrivalRate: 200
      rampTo: 20
      name: 'Ramp down'
  payload:
    path: './payload.csv'
    fields:
      - 'userId'
      - 'couponId'
scenarios:
  - name: "쿠폰 발급"
    flow:
      - post:
          url: "/api/coupons/download"
          json:
            userId: "{{ userId }}"
            couponId: "{{ couponId }}"
```
- 테스트 시나리오 세팅 : 
  - Warm up : 30초 동안 초당 10명의 유저가 접속
  - Ramp up : 20초 동안 초당 20명의 유저가 접속, 초당 200명의 유저까지 접속률을 증가
  - Sustained max load : 20초 동안 초당 200명의 유저가 접속
  - Ramp down : 30초 동안 초당 200명의 유저가 접속, 초당 20명의 유저까지 접속률을 감소

## 부하 테스트 결과
https://app.artillery.io/olej5drvnso5b/load-tests/t5nq6_nfqnw4fb8tjxbx9yfxaa6xgpcry6w_qjtd
