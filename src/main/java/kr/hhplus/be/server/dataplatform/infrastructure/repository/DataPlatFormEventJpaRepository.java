package kr.hhplus.be.server.dataplatform.infrastructure.repository;

import kr.hhplus.be.server.dataplatform.domain.entity.DataPlatFormEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataPlatFormEventJpaRepository extends JpaRepository<DataPlatFormEvent, Long> {


    List<DataPlatFormEvent> findByStatus(String status);

    DataPlatFormEvent findByAggregateId(Long orderId);
}
