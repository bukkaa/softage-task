package ru.softage.task.crud.db.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.softage.task.crud.db.entities.WorkerEntity;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static ru.softage.task.crud.db.entities.WorkerEntity.DELETE_WORKER_BY_ID;
import static ru.softage.task.crud.db.entities.WorkerEntity.FIND_WORKERS;

/**
 * Repository for CRUD operations on {@link WorkerEntity} (table 'workers')
 */
@Repository
public class WorkerRepository extends CommonRepository<WorkerEntity> {

    public WorkerRepository(EntityManager em) {
        super(em);
    }


    @Override
    @Transactional(propagation = NOT_SUPPORTED)
    public WorkerEntity findById(Long id) {
        return findById(id, WorkerEntity.class);
    }

    @Override
    @Transactional(propagation = NOT_SUPPORTED)
    public List<WorkerEntity> findAll() {
        return findAll(WorkerEntity.class, FIND_WORKERS);
    }

    @Override
    @Transactional(propagation = REQUIRED)
    public String deleteById(Long id) {
        return deleteById(WorkerEntity.class, DELETE_WORKER_BY_ID, id);
    }
}
