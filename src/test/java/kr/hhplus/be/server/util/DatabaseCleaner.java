package kr.hhplus.be.server.util;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseCleaner implements InitializingBean {

    private final List<String> tableNames = new ArrayList<>();

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void findDatabaseTableNames() {
        // 테이블 이름 가져오기
        List<String> tables = entityManager.createNativeQuery("SHOW TABLES").getResultList();
        // 테이블 이름을 백틱(`)으로 감싸기
        tables.forEach(table -> tableNames.add("`" + table + "`"));
    }

    private void truncate() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        for (String tableName : tableNames) {
            entityManager.createNativeQuery(String.format("TRUNCATE TABLE %s", tableName)).executeUpdate();
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

    @Override
    public void afterPropertiesSet() {}

    @Transactional
    public void clear() {
        // 영속성 컨텍스트 초기화
        entityManager.clear();
        truncate();
    }

}
