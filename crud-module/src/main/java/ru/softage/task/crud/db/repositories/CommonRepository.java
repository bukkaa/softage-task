package ru.softage.task.crud.db.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.softage.task.crud.db.entities.AbstractEntity;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static ru.softage.task.crud.db.entities.AbstractEntity.PARAMETER_ID;
import static ru.softage.task.api.tools.CollectionUtils.collectionToString;

/**
 * Abstract Repository with most common methods implemented.
 * @param <E> target entity class
 */
@Repository
public abstract class CommonRepository<E extends AbstractEntity> {
    private static final Logger log = LoggerFactory.getLogger(CommonRepository.class);

    final EntityManager em;

    @Autowired
    public CommonRepository(EntityManager em) {
        this.em = em;
    }


    public abstract E findById(Long id);

    public abstract List<E> findAll();

    public abstract String deleteById(Long id);


    @Transactional(propagation = REQUIRED)
    public void update(E entity) {
        log.info("update <- {} = {}", entity.getClass().getSimpleName(), entity);

        em.merge(entity);

        log.info("update -> {} = {}", entity.getClass().getSimpleName(), entity);
    }

    @Transactional(propagation = REQUIRED)
    public void persistAndFlush(E entity) {
        log.info("persistAndFlush <- {} = {}", entity.getClass().getSimpleName(), entity);

        em.persist(entity);
        em.flush();

        log.info("persistAndFlush -> {} = {}", entity.getClass().getSimpleName(), entity);
    }

    E findById(Long id, Class<E> entityClass){
        log.info("findById <- id = {}, entity = '{}'", id, entityClass.getSimpleName());

        E entity = em.find(entityClass, id);

        log.info("findById -> entity = {}", entity);
        return entity;
    }

    List<E> findAll(Class<E> entityClass, String queryName) {
        log.info("findAll <- entity = '{}'", entityClass.getSimpleName());

        List<E> list = em.createNamedQuery(queryName, entityClass).getResultList();

        log.info("findAll -> found: [{}]", collectionToString(list));
        return list;
    }

    String deleteById(Class<E> entityClass, String queryName, Long id) {
        log.info("delete <- id = {}, entity = '{}'", id, entityClass.getSimpleName());

        int i = em.createNamedQuery(queryName)
                .setParameter(PARAMETER_ID, id)
                .executeUpdate();
        log.debug("number of entities deleted: {}", i);

        String status = i > 0 ? "deleted" : "nothing to delete";

        log.info("delete -> status = '{}'", status);
        return status;
    }
}
