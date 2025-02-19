package kr.hhplus.be.server.dataplatform.infrastructure.repositoryImpl;

import kr.hhplus.be.server.dataplatform.domain.DataPlatFormRepository;
import kr.hhplus.be.server.dataplatform.domain.entity.DataPlatFormEvent;
import kr.hhplus.be.server.dataplatform.infrastructure.repository.DataPlatFormEventJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataPlatFormRepositoryImpl implements DataPlatFormRepository {

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
