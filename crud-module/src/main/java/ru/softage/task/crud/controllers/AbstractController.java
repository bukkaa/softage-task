package ru.softage.task.crud.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.softage.task.api.Response;
import ru.softage.task.api.models.AbstractModel;
import ru.softage.task.crud.ServiceException;
import ru.softage.task.crud.ServiceFormatException;
import ru.softage.task.crud.db.entities.AbstractEntity;
import ru.softage.task.crud.db.repositories.CommonRepository;

import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static ru.softage.task.api.Code.INTERNAL_ERROR;

@SuppressWarnings("unchecked")
abstract class AbstractController<M extends AbstractModel, E extends AbstractEntity, R extends CommonRepository> {
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    final R repository;

    @Autowired
    public AbstractController(R repository) {
        this.repository = repository;
    }


    abstract E toEntity(M model);

    abstract M toModel(E entity);


    Response findById(String methodName, Long id) {
        log.info("request {} <- id: {}", methodName, id);
        Response response = new Response();

        try {
            validate(id);

            E entity = (E) repository.findById(id);

            makeGetResponse(response, entity);
        } catch (ServiceFormatException e) {
            log.error("Format error: " + e.getMessage());
            response = new Response()
                    .setCode(e.getCode())
                    .setDescription("Format error: " + e.getMessage());
        } catch (ServiceException e) {
            log.error("Processing error: " + e.getMessage());
            response = new Response()
                    .setCode(e.getCode())
                    .setDescription("Processing error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: " + e.getMessage());
            response = new Response()
                    .setCode(INTERNAL_ERROR)
                    .setDescription("Unexpected error: " + e.getMessage());
        } finally {
            log.info("response {} -> {}", methodName, response);
        }

        return response;
    }

    Response findAll(String methodName) {
        log.info("request {} <- ", methodName);
        Response response = new Response();

        try {
            List<E> entities = repository.findAll();

            makeGetResponse(response, entities);
        } catch (ServiceFormatException e) {
            log.error("Format error: " + e.getMessage());
            response = new Response()
                    .setCode(e.getCode())
                    .setDescription("Format error: " + e.getMessage());
        } catch (ServiceException e) {
            log.error("Processing error: " + e.getMessage());
            response = new Response()
                    .setCode(e.getCode())
                    .setDescription("Processing error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: " + e.getMessage());
            response = new Response()
                    .setCode(INTERNAL_ERROR)
                    .setDescription("Unexpected error: " + e.getMessage());
        } finally {
            log.info("response {} -> {}", methodName, response);
        }

        return response;
    }

    Response persistOrUpdate(String methodName, M model) {
        log.info("request {} <- {}: {}", methodName, model.getClass().getSimpleName(), model);
        Response response = new Response();

        try {
            // no ID in request == new object
            boolean isNewEntity = model.id == null;

            // no need to validate missing fields while updating
            if (isNewEntity) {
                validate(model);
            }

            E entity = toEntity(model);

            log.debug("entity created: {}", entity);

            persistOrUpdate(entity, isNewEntity);

            response
                    .setDescription(isNewEntity ? "created" : "updated")
                    .setEntities(singletonList(toModel(entity)));
        } catch (ServiceFormatException e) {
            log.error("Format error: " + e.getMessage());
            response = new Response()
                    .setCode(e.getCode())
                    .setDescription("Format error: " + e.getMessage());
        } catch (ServiceException e) {
            log.error("Processing error: " + e.getMessage());
            response = new Response()
                    .setCode(e.getCode())
                    .setDescription("Processing error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: " + e.getMessage());
            response = new Response()
                    .setCode(INTERNAL_ERROR)
                    .setDescription("Unexpected error: " + e.getMessage());
        } finally {
            log.info("response {} -> {}", methodName, response);
        }

        return response;
    }

    Response deleteById(String methodName, Long id) {
        log.info("request {} <- id: {}", methodName, id);
        Response response = new Response();

        try {
            validate(id);

            String status = repository.deleteById(id);

            response.setDescription(status);
        } catch (ServiceFormatException e) {
            log.error("Format error: " + e.getMessage());
            response = new Response()
                    .setCode(e.getCode())
                    .setDescription("Format error: " + e.getMessage());
        } catch (ServiceException e) {
            log.error("Processing error: " + e.getMessage());
            response = new Response()
                    .setCode(e.getCode())
                    .setDescription("Processing error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: " + e.getMessage());
            response = new Response()
                    .setCode(INTERNAL_ERROR)
                    .setDescription("Unexpected error: " + e.getMessage());
        } finally {
            log.info("response {} -> {}", methodName, response);
        }

        return response;
    }

    void validate(Long id) {
        if (id != null) {
            if (id < 0) {
                log.error("ID number must be non-negative, but id = {}", id);
                throw new ServiceFormatException(format("ID number must be non-negative, but id = %d; ", id));
            }
        }

        log.info("validated successful: id = {}", id);
    }

    void validate(M model){
        if (model == null) {
            log.error("Persisting model must not be null");
            throw new ServiceFormatException("Persisting model must not be null");
        }
    }

    void makeGetResponse(Response response, List<E> entities) {
        if (entities != null && !entities.isEmpty()) {
            response.setEntities(toModel(entities));
        } else {
            response.setDescription("no result");
        }
    }


    private void makeGetResponse(Response response, E entity) {
        if (entity != null) {
            response.setEntities(singletonList(toModel(entity)));
        } else {
            response.setDescription("no result");
        }
    }

    private void persistOrUpdate(E entity, boolean newEntity) {
        if (newEntity) {
            log.info("persisting...");
            repository.persistAndFlush(entity);
        } else {
            log.info("updating...");
            repository.update(entity);
        }
    }

    private List<M> toModel(List<E> entities){
        return entities.stream().map(this::toModel).collect(toList());
    }

}
