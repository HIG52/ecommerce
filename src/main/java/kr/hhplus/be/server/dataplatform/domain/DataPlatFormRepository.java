package kr.hhplus.be.server.dataplatform.domain;

import kr.hhplus.be.server.dataplatform.domain.entity.DataPlatFormEvent;

import java.util.List;

public interface DataPlatFormRepository {
    void save(DataPlatFormEvent dataPlatFormEvent);

    List<DataPlatFormEvent> findByStatus(String status);

    DataPlatFormEvent findByAggregateId(Long orderId);
}
