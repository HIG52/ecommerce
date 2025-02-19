package kr.hhplus.be.server.dataplatform.infrastructure.repository;

import kr.hhplus.be.server.dataplatform.domain.entity.DataPlatFormEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataPlatFormEventJpaRepository extends JpaRepository<DataPlatFormEvent, Long> {


}
