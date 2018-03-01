package ru.softage.task.crud.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.softage.task.api.Response;
import ru.softage.task.api.models.WorkerModel;
import ru.softage.task.crud.ServiceFormatException;
import ru.softage.task.crud.db.entities.WorkerEntity;
import ru.softage.task.crud.db.repositories.WorkerRepository;

import static java.lang.String.format;

@RestController
public class WorkersController extends
        AbstractController<WorkerModel, WorkerEntity, WorkerRepository> {
    private static final Logger log = LoggerFactory.getLogger(WorkersController.class);

    public WorkersController(WorkerRepository repository) {
        super(repository);
    }


    @Override
    void validate(WorkerModel model) {
        super.validate(model);

        String message = "";

        if (model.id != null) {
            if (model.id < 0L) {
                message += format("ID number must be non-negative, but id = %d; ", model.id);
            }
        }

        if (StringUtils.isEmpty(model.name)) {
            message += "Worker 'name' is absent; ";
        }

        if (StringUtils.isEmpty(model.lastName)) {
            message += "Worker 'lastName' is absent; ";
        }

        if (StringUtils.isEmpty(model.position)) {
            message += "Worker 'position' is absent; ";
        }

        if (model.birthday == null) {
            message += "Worker 'birthday' is absent; ";
        }

        if (model.hiringDate == null) {
            message += "Worker 'hiringDate' is absent; ";
        }

        // resignationDate can be null

        if (model.departmentId == null) {
            message += "Department ID number is absent; ";
        } else {
            if (model.departmentId < 0L) {
                message += format("Department ID number must be non-negative, but id = %d; ", model.departmentId);
            }
        }
        if (!message.isEmpty()) {
            throw new ServiceFormatException(message);
        }

        log.info("validated successful: {} = {}", model.getClass().getSimpleName(), model);
    }

    @Override
    WorkerEntity toEntity(WorkerModel model) {
        log.debug("toEntity <- model: {}", model);
        return new WorkerEntity()
                .setId(model.id)
                .setName(model.name)
                .setLastName(model.lastName)
                .setBirthday(model.birthday)
                .setPosition(model.position)
                .setDepartmentId(model.departmentId)
                .setHiringDate(model.hiringDate)
                .setResignationDate(model.resignationDate);
    }

    @Override
    WorkerModel toModel(WorkerEntity entity) {
        return new WorkerModel()
                .setId(entity.id)
                .setName(entity.name)
                .setLastName(entity.lastName)
                .setBirthday(entity.birthday)
                .setPosition(entity.position)
                .setDepartmentId(entity.departmentId)
                .setHiringDate(entity.hiringDate)
                .setResignationDate(entity.resignationDate);
    }


    @GetMapping("/workers/{id}")
    public @ResponseBody Response getWorkersById(@PathVariable(name = "id") Long id) {
        return findById("getWorkersById", id);
    }

    @GetMapping("/workers")
    public @ResponseBody Response getWorkers() {
        return findAll("getWorkers");
    }

    @PostMapping("/workers")
    public @ResponseBody Response persistOrUpdateWorker(@RequestBody WorkerModel model) {
        return persistOrUpdate("persistOrUpdateWorker", model);
    }

    @DeleteMapping("/workers/{id}")
    public @ResponseBody Response deleteWorkerById(@PathVariable("id") Long id) {
        return deleteById("deleteWorkerById", id);
    }
}
