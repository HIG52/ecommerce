package kr.hhplus.be.server.dataplatform.domain;

import kr.hhplus.be.server.dataplatform.domain.entity.DataPlatFormEvent;

public interface DataPlatFormRepository {
    void save(DataPlatFormEvent dataPlatFormEvent);
}
