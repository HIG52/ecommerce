package kr.hhplus.be.server.dataplatform.infrastructure.repositoryImpl;

import kr.hhplus.be.server.dataplatform.domain.DataPlatFormRepository;
import kr.hhplus.be.server.dataplatform.domain.entity.DataPlatFormEvent;
import kr.hhplus.be.server.dataplatform.infrastructure.repository.DataPlatFormEventJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataPlatFormRepositoryImpl implements DataPlatFormRepository {
    @Override
    public List<DataPlatFormEvent> findByStatus(String status) {
        return dataPlatFormEventJpaRepository.findByStatus(status);
    }

    @Override
    public DataPlatFormEvent findByAggregateId(Long orderId) {
        return dataPlatFormEventJpaRepository.findByAggregateId(orderId);
    }

    private final DataPlatFormEventJpaRepository dataPlatFormEventJpaRepository;

    @Autowired
    public DataPlatFormRepositoryImpl(DataPlatFormEventJpaRepository dataPlatFormEventJpaRepository) {
        this.dataPlatFormEventJpaRepository = dataPlatFormEventJpaRepository;
    }

    @Override
    public void save(DataPlatFormEvent dataPlatFormEvent) {
        dataPlatFormEventJpaRepository.save(dataPlatFormEvent);
    }

}
