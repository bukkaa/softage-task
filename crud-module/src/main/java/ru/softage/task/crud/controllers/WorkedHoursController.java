package ru.softage.task.crud.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.softage.task.api.Response;
import ru.softage.task.api.models.WorkedHoursModel;
import ru.softage.task.crud.ServiceException;
import ru.softage.task.crud.ServiceFormatException;
import ru.softage.task.crud.db.entities.WorkedHoursEntity;
import ru.softage.task.crud.db.repositories.WorkedHoursRepository;

import java.util.List;

import static java.lang.String.format;
import static ru.softage.task.api.Code.INTERNAL_ERROR;

@RestController
public class WorkedHoursController extends
        AbstractController<WorkedHoursModel, WorkedHoursEntity, WorkedHoursRepository>{
    private static final Logger log = LoggerFactory.getLogger(WorkedHoursController.class);


    public WorkedHoursController(WorkedHoursRepository repository) {
        super(repository);
    }


    @Override
    void validate(WorkedHoursModel model) {
        super.validate(model);

        String message = "";

        if (model.id != null) {
            if (model.id < 0L) {
                message += format("ID number must be non-negative, but id = %d; ", model.id);
            }
        }

        if (model.startDate == null) {
            message += "WorkedHours 'startDate' is absent; ";
        }

        // finishDate can be null

        if (model.workerId == null) {
            message += "Worker ID number is absent; ";
        } else {
            if (model.workerId < 0L) {
                message += format("Worker ID number must be non-negative, but id = %d; ", model.workerId);
            }
        }

        if (!message.isEmpty()) {
            throw new ServiceFormatException(message);
        }

        log.info("validated successful: {} = {}", model.getClass().getSimpleName(), model);
    }

    @Override
    WorkedHoursEntity toEntity(WorkedHoursModel model) {
        return new WorkedHoursEntity()
                .setId(model.id)
                .setStartDate(model.startDate)
                .setFinishDate(model.finishDate)
                .setWorkerId(model.workerId);
    }

    @Override
    WorkedHoursModel toModel(WorkedHoursEntity entity) {
        return new WorkedHoursModel()
                .setId(entity.id)
                .setStartDate(entity.startDate)
                .setFinishDate(entity.finishDate)
                .setWorkerId(entity.workerId);
    }


    @GetMapping("/worked_hours/{workerId}")
    public @ResponseBody Response getWorkedHoursByWorkerId(@PathVariable(name = "workerId") Long workerId) {
        log.info("request getWorkedHoursByWorkerId <- id: {}", workerId);
        Response response = new Response();

        try {
            validate(workerId);

            List<WorkedHoursEntity> entities = repository.findByWorkerId(workerId);

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
            log.info("response getWorkedHoursByWorkerId -> {}", response);
        }

        return response;
    }

    @GetMapping("/worked_hours")
    public @ResponseBody Response getAllWorkedHours() {
        return findAll("getAllWorkedHours");
    }

    @PostMapping("/worked_hours")
    public @ResponseBody Response persistOrUpdateWorkedHours(@RequestBody WorkedHoursModel model) {
        return persistOrUpdate("persistOrUpdateWorkedHours", model);
    }

    @DeleteMapping("/worked_hours/{workerId}")
    public @ResponseBody Response deleteWorkedHoursByWorkerId(@PathVariable("workerId") Long workerId) {
        return deleteById("deleteWorkedHoursByWorkerId", workerId);
    }
}
