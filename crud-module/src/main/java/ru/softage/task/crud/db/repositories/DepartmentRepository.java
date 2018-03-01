package ru.softage.task.crud.db.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.softage.task.crud.db.entities.DepartmentEntity;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static ru.softage.task.crud.db.entities.DepartmentEntity.DELETE_DEPT_BY_ID;
import static ru.softage.task.crud.db.entities.DepartmentEntity.FIND_DEPARTMENTS;

/**
 * Repository for CRUD operations on {@link DepartmentEntity} (table 'departments')
 */
@Repository
public class DepartmentRepository extends CommonRepository<DepartmentEntity> {

    public DepartmentRepository(EntityManager em) {
        super(em);
    }


    @Override
    @Transactional(propagation = NOT_SUPPORTED)
    public DepartmentEntity findById(Long id) {
        return findById(id, DepartmentEntity.class);
    }

    @Override
    @Transactional(propagation = NOT_SUPPORTED)
    public List<DepartmentEntity> findAll() {
        return findAll(DepartmentEntity.class, FIND_DEPARTMENTS);
    }

    @Override
    @Transactional(propagation = REQUIRED)
    public String deleteById(Long id) {
        return deleteById(DepartmentEntity.class, DELETE_DEPT_BY_ID, id);
    }
}
