package ru.softage.task.crud.db.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.softage.task.crud.ServiceException;
import ru.softage.task.crud.db.entities.WorkedHoursEntity;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static ru.softage.task.api.Code.INTERNAL_ERROR;
import static ru.softage.task.api.tools.CollectionUtils.collectionToString;
import static ru.softage.task.crud.db.entities.WorkedHoursEntity.*;

/**
 * Repository for CRUD operations on {@link WorkedHoursEntity} (table 'worked_hours')
 */
@Repository
public class WorkedHoursRepository extends CommonRepository<WorkedHoursEntity> {
    private static final Logger log = LoggerFactory.getLogger(WorkedHoursRepository.class);

    public WorkedHoursRepository(EntityManager em) {
        super(em);
    }


    @Override
    @Transactional(propagation = NOT_SUPPORTED)
    public WorkedHoursEntity findById(Long id) {
        log.error("Method 'findById' must not be called from WorkedHoursController");
        throw new ServiceException(INTERNAL_ERROR, "Method 'findById' must not be called from WorkedHoursController");
    }

    @Transactional(propagation = NOT_SUPPORTED)
    public List<WorkedHoursEntity> findByWorkerId(Long workerId) {
       log.info("findByWorkerId <- worker id = {}", workerId);

       List<WorkedHoursEntity> list = em.createNamedQuery(FIND_WORKED_HOURS_BY_WORKER_ID, WorkedHoursEntity.class)
               .setParameter(PARAMETER_ID, workerId)
               .getResultList();

       log.info("findByWorkerId -> worked hours: [{}]", collectionToString(list));
       return list;
    }

    @Override
    @Transactional(propagation = NOT_SUPPORTED)
    public List<WorkedHoursEntity> findAll() {
        return findAll(WorkedHoursEntity.class, FIND_WORKED_HOURS);
    }

    @Override
    @Transactional(propagation = REQUIRED)
    public String deleteById(Long workerId) {
        return deleteById(WorkedHoursEntity.class, DELETE_WORKED_HOURS_BY_WORKER_ID, workerId);
    }
}
