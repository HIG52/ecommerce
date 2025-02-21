# 자주 조회, 복잡한 쿼리의 성능 개선

---

## 목차
1. 쿼리 분석 및 인덱스 추가
    - [쿼리 수집 및 분석](#11-쿼리-수집-및-분석)
    - [실행 계획(EXPLAIN) 분석을 통한 성능 저하 원인](#12-실행-계획explain-분석을-통한-성능-저하-원인)
   
2. 인덱스 개념 및 최적화 학습
   - [기본 인덱스와 복합 인덱스의 차이](#21-기본-인덱스와-복합-인덱스의-차이)
   - [B-Tree, Hash 인덱스 구조 및 동작 방식](#22-b-tree-hash-인덱스-구조-및-동작-방식)
   - [인덱스가 쿼리 성능에 미치는 영향 (Where, Join, Order By, Group By 절에서의 인덱스 활용 방식)](#23-인덱스가-인덱스가-쿼리-성능에-미치는-영향)
   - [인덱스가 비효율적으로 동작하는 경우 및 해결 방안](#24-인덱스가-비효율적으로-동작하는-경우-및-해결-방안)

3. 인덱스 적용 및 성능 비교
    - [수집된 쿼리에 대한 인덱스 필요성 검토](#31-수집된-쿼리에-대한-인덱스-필요성-검토)
    - [적절한 인덱스 설계 및 적용 (단일 vs 복합 인덱스)](#32-적절한-인덱스-설계-및-적용)
    - [기존 인덱스와 중복 여부 점검 및 불필요한 인덱스 정리](#33-기존-인덱스와-중복-여부-점검-및-불필요한-인덱스-정리)
    - [인덱스 추가 전후 실행 계획(EXPLAIN) 및 쿼리 성능 비교](#34-인덱스-추가-전후-실행-계획explain-및-쿼리-성능-비교)
   
4. 결과
    - [결과](#결과)

5. 참고
   - [링크 리스트](#참고) 

---

## 1. 쿼리 분석 및 인덱스 추가

### 1.1 쿼리 수집 및 분석

- 쿼리 목적 : 지난 3일간의 주문 내역(order_detail 테이블) 에서 각 상품(product_id)별 주문 수량
의 합계를 계산하고, 해당 합계가 높은 상위 5개의 상품을 조회한다.


- 쿼리 구조 :
    ```
    SELECT product_id AS product_id
    FROM order_detail
    WHERE created_at BETWEEN NOW() - INTERVAL 3 DAY AND NOW()
    GROUP BY product_id
    ORDER BY SUM(order_quantity) DESC
    LIMIT 5;
    ```
  - WHERE : `create_at` 컬럼에 대해 최근 3일간의 데이터로 범위를 지정해준다.
    
      &rarr; 시간 범위 필터링
  
  - GROUP BY : 상품별(product_id)로 그룹화 해준다.

    &rarr; 각 상품에 대한 주문 집계가 이루어짐
  
  - 집계 함수 : 
    - `SUM(order_quantity)` : 각 그룹의 총 주문 수량을 계산한다.

  - ORDER BY : `SUM(order_quantity)` 의 내림차순 정렬

    &rarr; 주문 수량이 많은 상품 순으로 정렬시켜준다.

  - LIMIT : 상위 5개의 결과만 출력한다.

- 분석 : 
  - 필터링 : `create_at` 컬럼에 인덱스가 없다면 날짜 범위에 해당하는 레코드를 찾기위해 full scan 을 할수 있음
  - 그룹화 및 정렬 : 대량의 데이터에 대해 그룹화 및 정렬을 하므로 적절한 인덱스가 없다면 성능 저하가 올수 있음

### 1.2 실행 계획(EXPLAIN) 분석을 통한 성능 저하 원인

- EXPLAIN 실행 : 
```
EXPLAIN ANALYZE
SELECT product_id AS product_id
FROM order_detail
WHERE created_at BETWEEN NOW() - INTERVAL 3 DAY AND NOW()
GROUP BY product_id
ORDER BY SUM(order_quantity) DESC
LIMIT 5;
```

- EXPLAIN 실행 결과 : 약 200~250ms

|   id   |select_type|    table     | partitions | type | possible_keys | key | key_len | ref |  rowd  | filtered |                   Extra                    |
|:------:|:--:|:------------:|:----------:|:----:|:-------------:|:---:|:-------:|:---:|:------:|:--------:|:------------------------------------------:|
| 1 |SIMPLE| order_detail |            | ALL  |               |     |         |     | 996779 |  11.11   |Using where; Using temporary; Using filesort|

- Limit: 5 row(s)  (cost=2.6..2.6 rows=0) (actual time=366..366 rows=5 loops=1)
- Sort: derived.total_quantity DESC, limit input to 5 row(s) per chunk  (cost=2.6..2.6 rows=0) (actual time=366..366 rows=5 loops=1)
- Table scan on derived  (cost=2.5..2.5 rows=0) (actual time=366..366 rows=20 loops=1)
- Materialize  (cost=0..0 rows=0) (actual time=366..366 rows=20 loops=1)
- Table scan on <temporary>  (actual time=366..366 rows=20 loops=1)
- Aggregate using temporary table  (actual time=366..366 rows=20 loops=1)
- Filter: (order_detail.created_at between <cache>((now() - interval 3 day)) and <cache>(now()))  (cost=12840 rows=110854) (actual time=0.516..305 rows=271700 loops=1)
- Table scan on order_detail  (cost=12840 rows=997782) (actual time=0.492..259 rows=1e+6 loops=1)

- 문제점 :
  1. order_detail 테이블의 전체 스캔 (Full Table Scan)
     - 마지막 단계에서 order_detail 테이블을 1,000,000건 정도 스캔하는데, 이때 인덱스 없이 모든 행을 읽으므로 많은 비용이 발생한다.
  2. 임시 테이블 생성 및 정렬 (Using temporary; Using filesort)
     - GROUP BY, ORDER BY 절에서 임시 테이블을 생성하고 정렬을 수행하는데, 이로 인해 성능 저하가 발생한다.

- 개선 방안 : 
  - 인덱스 추가 : `create_at` 컬럼의 인덱스 추가 또는 상황에 따라 `(create_at, product_id, order_quantity)` 와 같은 복합인덱스를 사용한다.

---

## 2. 인덱스 개념 및 최적화 학습

### 2.1 기본 인덱스와 복합 인덱스의 차이

- 단일 인덱스 : 인덱스에 하나의 컬럼만 사용


- 복합 인덱스 : 인덱스에 두 개 이상의 컬럼을 사용 


- 차이점 : 
  - 하나의 조건에 대해서는 단일 인덱스가 효율적이고 여러 조건이 동시에 적용되는 경우에는 복합인덱스가 조금더 유리하다
  - 복합 인덱스는 인덱스의 선행 컬럼 순서에 따라 조건절 별로 활용 여부가 달라진다

### 2.2 B-Tree, Hash 인덱스 구조 및 동작 방식
- B-Tree 인덱스 :  
  - 데이터 베이스의 인덱스 알고리즘 가운데 가장 일반적으로 사용되는 알고리즘
  - 이름처럼 트리구조로 최상위에 하나의 `루트 노드`가 존재하고, 하위에 자식노드가 붙어있는 형태
  - 루트 노드, 브랜치 노드, 리프 노드 형태로 가지고 있으며 가장 하위인 리프 노드는 항상 실제 데이터 레코드를 찾아가지 위한 주솟값을 가지고 있다.

  &rarr; B-Tree 는 진짜 B- 라는 의미와 균형트리라는 의미 두가지가 있는데 MySQL 도큐먼트에서는 후자로 쓰이고 실제로 DB 에서는 대부분 B+Tree 로 사용한다

- Hash 인덱스 : 
  - 해시 테이블 자료구조로 컬럼값과 메모리 주소를 관리한다.
  - 특정 컬럼에 대해 Hash 인덱스를 설정할 경우, 해당 컬럼 값에 해시 함수를 적용하여 값을 얻고 이 해시 값에 해당하는 위치에 컬럼값 원본과 해당 로우가 저장된 위치를 제공한다.
  - `(=)` 비교에 특화, 범위 검색에는 부적합 하다.

### 2.3 인덱스가 인덱스가 쿼리 성능에 미치는 영향
- WHERE : 인덱스가 존재하면 조건에 맞는 데이터에 빠르게 접근할수 있어 full scan 을 피할수 있다.
- JOIN : 조인 대상 컬럼에 인덱스가 있다면 매칭되는 레코드를 빠르게 찾을수 있다.
- ORDER BY : 정렬을 위한 인덱스를 사용하면 별도의 정렬 작업 없이 인덱스 순서대로 데이터를 읽어올 수 있다.
- GROUP BY : 인덱스 순서대로 그룹화할 수 있다면 임시 테이블 생성등 불필요한 절차를 줄일 수 있다.

### 2.4 인덱스가 비효율적으로 동작하는 경우 및 해결 방안
- 비효율적 상황 : 
  - 컬럼 순서가 잘못될 경우
  - Selectivity 가 낮을 경우
  - 불필요하거나 중복되는 인덱스가 많아질 경우, 삽입/수정/삭제 시 성능 하락
- 해결 방안
  - 쿼리에 맞는 인덱스 컬럼 순서를 지정
  - 주기적으로 EXPLAIN 을 점검해서 인덱스를 유지보수 한다.
  - 사용 빈도가 낮거나 중복되는 인덱스는 제거 한다.

---

## 3. 인덱스 적용 및 성능 비교

### 3.1 수집된 쿼리에 대한 인덱스 필요성 검토
- 필터링 조건 :
  - WHERE 절의 `created_at BETWEEN ...` 조건에 의해, 최근 3일간의 데이터를 효율적으로 조회하기 위해서는 created_at 컬럼에 인덱스가 필요함
- 그룹 및 정렬 조건 : 
  - GROUP BY 대상인 `product_id` 역시 인덱스 활용 시 그룹핑 연산의 비용을 낮출 수 있음
- 결론 :
  - 단일 인덱스(예: `created_at` 또는 `product_id`)보다, 두 컬럼을 포함하는 복합 인덱스가 성능 개선에 더 효과적일 것으로 판단됨

### 3.2 적절한 인덱스 설계 및 적용
- 복합 인덱스 적용
```
CREATE INDEX idx_order_detail_created_product
    ON order_detail (created_at, product_id);
```
- `created_at`을 선두 컬럼으로 두어 범위 검색 조건을 효율적으로 처리한다.
- 뒤따르는 `product_id`를 포함하여 그룹화 연산 시 인덱스 스캔으로 데이터 접근을 빠르게 한다.
- 상황에 따라 데이터 분포와 쿼리 빈도를 고려하여 인덱스를 조정 할 수도 있다.

### 3.3 기존 인덱스와 중복 여부 점검 및 불필요한 인덱스 정리
- 기존에 인덱스가 생성되어있지 않기 때문에 복합인덱스를 적용 한다 (`SHOW INDEX FROM order_detail`)

### 3.4 인덱스 추가 전후 실행 계획(EXPLAIN) 및 쿼리 성능 비교
- 인덱스 추가 전 :

|   id   |select_type|    table     | partitions | type | possible_keys | key | key_len | ref |  rowd  | filtered |                   Extra                    |
|:------:|:--:|:------------:|:----------:|:----:|:-------------:|:---:|:-------:|:---:|:------:|:--------:|:------------------------------------------:|
| 1 |SIMPLE| order_detail |            | ALL  |               |     |         |     | 996779 |  11.11   |Using where; Using temporary; Using filesort|

- Limit: 5 row(s)  (cost=2.6..2.6 rows=0) (actual time=366..366 rows=5 loops=1)
- Sort: derived.total_quantity DESC, limit input to 5 row(s) per chunk  (cost=2.6..2.6 rows=0) (actual time=366..366 rows=5 loops=1)
- Table scan on derived  (cost=2.5..2.5 rows=0) (actual time=366..366 rows=20 loops=1)
- Materialize  (cost=0..0 rows=0) (actual time=366..366 rows=20 loops=1)
- Table scan on <temporary>  (actual time=366..366 rows=20 loops=1)
- Aggregate using temporary table  (actual time=366..366 rows=20 loops=1)
- Filter: (order_detail.created_at between <cache>((now() - interval 3 day)) and <cache>(now()))  (cost=12840 rows=110854) (actual time=0.516..305 rows=271700 loops=1)
- Table scan on order_detail  (cost=12840 rows=997782) (actual time=0.492..259 rows=1e+6 loops=1)


- 쿼리 수행 시간 : 약 200~250ms 소요


- 인덱스 추가 후 :

|   id   |select_type|    table     | partitions | type | possible_keys | key | key_len | ref |  rowd  | filtered |                   Extra                    |
|:------:|:--:|:------------:|:----------:|:----:|:-------------:|:---:|:-------:|:---:|:------:|:--------:|:------------------------------------------:|
| 1 |SIMPLE| order_detail |            | range  | idx_order_detail_created_product_quantity | idx_order_detail_created_product_quantity |    8    |     | 455592 |  100   |Using where; Using index; Using temporary; Using filesort|


- Limit: 5 row(s)  (cost=2.6..2.6 rows=0) (actual time=119..119 rows=5 loops=1)
- Sort: derived.total_quantity DESC, limit input to 5 row(s) per chunk  (cost=2.6..2.6 rows=0) (actual time=119..119 rows=5 loops=1)
- Table scan on derived  (cost=2.5..2.5 rows=0) (actual time=119..119 rows=20 loops=1)
- Materialize  (cost=0..0 rows=0) (actual time=119..119 rows=20 loops=1)
- Table scan on <temporary>  (actual time=119..119 rows=20 loops=1)
- Aggregate using temporary table  (actual time=119..119 rows=20 loops=1)
- Filter: (order_detail.created_at between <cache>((now() - interval 3 day)) and <cache>(now()))  (cost=101600 rows=498891) (actual time=0.36..63.5 rows=273457 loops=1)
- Covering index range scan on order_detail using idx_order_detail_created_product_quantity over ('2025-02-10 15:36:31.000000' <= created_at <= '2025-02-13 15:36:31.000000')  (cost=101600 rows=498891) (actual time=0.353..48.6 rows=273457 loops=1)


- 쿼리 수행 시간 : 약 100~150ms 소요

temporary를 사용하지 않기위하여 서브쿼리를 사용하고 인덱스또한 걸어봤지만 결국 기본 그룹바이에서 복합인덱스를 사용하는것이
가장 빠르게 나온것을 확인할수 있엇다. 이론상으로는 가장 빠르면 안됐지만 이것은 현재 들어가있는데이터의 차이라고도 볼수 있을거같다.

---

## 결과
보고서에서는 단순히 인덱스 적용 전후의 차이만을 기술하였으나, 실제 테스트에서는 날짜를 모두 동일하게 설정하고 동일한 복합 인덱스를 적용한 결과, 인덱스를 적용하지 않은 쿼리와 수행 시간이 유사하게 나타남을 확인할 수 있었습니다.


이 결과를 통해 인덱스를 설정할 때 단순히 "데이터 양이 많다"거나 "일반적인 컬럼 순서에 따른 정석적인 방법"에 의존하기보다는, 해당 컬럼의 데이터 분포와 중복 정도를 면밀히 분석한 후에 결정해야 한다는 점을 깨달았습니다.


또한, 한 번 인덱스를 적용했다고 해서 영구적으로 최적의 성능이 유지되는 것이 아니라, 주기적인 모니터링과 최적화가 필요하다는 사실도 확인할 수 있었습니다.


---

## 참고
https://dev.mysql.com/doc/refman/8.4/en/optimization-indexes.html

https://zorba91.tistory.com/293

https://mangkyu.tistory.com/286

https://letscodehappily.tistory.com/76

책 : Real Mysql 8.0