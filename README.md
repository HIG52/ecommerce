## 이커머스

### 마일스톤
https://github.com/users/HIG52/projects/1

### 플로우 차트
https://docs.google.com/spreadsheets/d/1Cue3eMqK5F04tsCGB9OkR2Aas16xIATcTdBCDQwc_Rk/edit?usp=sharing

### 유저 시나리오
https://docs.google.com/spreadsheets/d/1vLrqDUHTwnx68Rff7oCwebgZ_qREFQlPvPCHrOoyims/edit?usp=sharing

### 시퀀스 다이어그램
https://docs.google.com/spreadsheets/d/19ekSsXp_MItYbaY5A5JpfeulXQ6UXXZiagSQtws4Bwc/edit?usp=sharing

### API 명세서
https://docs.google.com/spreadsheets/d/1S-V0aaEDx8nXaLeL-qKHYJz45HS5AnGflVhSwqqUvAA/edit?usp=sharing

### 스웨거
https://app.swaggerhub.com/apis-docs/HINGJY1997_1/ecommerce/1.0.0

### ERD
https://docs.google.com/spreadsheets/d/18d7nosQLr5CIZH96ZxWk3UayW7eZdu1zySMU7XHcBEY/edit?usp=sharing

### 패키지 구조
```
├─main
│  ├─java
│  │  └─kr
│  │      └─hhplus
│  │          └─be
│  │              └─server
│  │                  ├─api
│  │                  │  ├─balance
│  │                  │  │  ├─domain
│  │                  │  │  │  ├─entity
│  │                  │  │  │  ├─repository
│  │                  │  │  │  └─service
│  │                  │  │  │      ├─request
│  │                  │  │  │      └─response
│  │                  │  │  ├─infrastructure
│  │                  │  │  │  ├─repository
│  │                  │  │  │  └─repositoryImpl
│  │                  │  │  └─presentation
│  │                  │  │      ├─controller
│  │                  │  │      ├─dto
│  │                  │  │      └─usecase
│  │                  │  ├─coupon
│  │                  │  │  ├─domain
│  │                  │  │  │  ├─entity
│  │                  │  │  │  ├─repository
│  │                  │  │  │  └─service
│  │                  │  │  │      ├─request
│  │                  │  │  │      └─response
│  │                  │  │  ├─infrastructure
│  │                  │  │  │  ├─repository
│  │                  │  │  │  └─repositoryImpl
│  │                  │  │  └─presentation
│  │                  │  │      ├─controller
│  │                  │  │      ├─dto
│  │                  │  │      └─usecase
│  │                  │  ├─order
│  │                  │  │  ├─domain
│  │                  │  │  │  ├─entity
│  │                  │  │  │  ├─repository
│  │                  │  │  │  └─service
│  │                  │  │  │      ├─request
│  │                  │  │  │      └─response
│  │                  │  │  ├─infrastructure
│  │                  │  │  │  ├─repository
│  │                  │  │  │  └─repositoryImpl
│  │                  │  │  └─presentation
│  │                  │  │      ├─controller
│  │                  │  │      ├─dto
│  │                  │  │      └─usecase
│  │                  │  ├─payment
│  │                  │  │  ├─domain
│  │                  │  │  │  ├─entity
│  │                  │  │  │  ├─repository
│  │                  │  │  │  └─service
│  │                  │  │  │      ├─request
│  │                  │  │  │      └─response
│  │                  │  │  ├─infrastructure
│  │                  │  │  │  ├─repository
│  │                  │  │  │  └─repositoryImpl
│  │                  │  │  └─presentation
│  │                  │  │      ├─controller
│  │                  │  │      ├─dto
│  │                  │  │      └─usecase
│  │                  │  └─product
│  │                  │      ├─domain
│  │                  │      │  ├─entity
│  │                  │      │  ├─repository
│  │                  │      │  └─service
│  │                  │      │      ├─request
│  │                  │      │      └─response
│  │                  │      ├─infrastructure
│  │                  │      │  ├─repository
│  │                  │      │  └─repositoryImpl
│  │                  │      └─presentation
│  │                  │          ├─controller
│  │                  │          ├─dto
│  │                  │          └─usecase
│  │                  ├─common
│  │                  │  ├─entity
│  │                  │  ├─error
│  │                  │  └─type
│  │                  ├─config
│  │                  │  ├─jpa
│  │                  │  └─swagger
│  │                  └─dataflatform
│  └─resources
└─test
    ├─java
    │  └─kr
    │      └─hhplus
    │          └─be
    │              └─server
    │                  ├─api
    │                  │  ├─balance
    │                  │  │  └─domain
    │                  │  │      └─service
    │                  │  ├─coupon
    │                  │  │  └─domain
    │                  │  │      └─service
    │                  │  ├─order
    │                  │  │  └─domain
    │                  │  │      └─service
    │                  │  ├─payment
    │                  │  │  └─domain
    │                  │  │      └─service
    │                  │  └─product
    │                  │      └─domain
    │                  │          └─service
    │                  └─integration
    └─resources
```