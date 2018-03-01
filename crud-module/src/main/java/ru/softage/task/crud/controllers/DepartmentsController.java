package ru.softage.task.crud.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.softage.task.api.Response;
import ru.softage.task.api.models.DepartmentModel;
import ru.softage.task.crud.ServiceFormatException;
import ru.softage.task.crud.db.entities.DepartmentEntity;
import ru.softage.task.crud.db.repositories.DepartmentRepository;

import static java.lang.String.format;

@RestController
public class DepartmentsController extends
        AbstractController<DepartmentModel, DepartmentEntity, DepartmentRepository> {
    private static final Logger log = LoggerFactory.getLogger(DepartmentsController.class);

    public DepartmentsController(DepartmentRepository repository) {
        super(repository);
    }


    @Override
    void validate(DepartmentModel model) {
        super.validate(model);

        String message = "";

        if (model.id != null) {
            if (model.id < 0L) {
                message += format("ID number must be non-negative, but id = %d; ", model.id);
            }
        }

        if (StringUtils.isEmpty(model.name)) {
            message += "Department 'name' is absent; ";
        }

        if (StringUtils.isEmpty(model.address)) {
            message += "Department 'address' is absent; ";
        }

        if (!message.isEmpty()) {
            throw new ServiceFormatException(message);
        }

        log.info("validated successful: {} = {}", model.getClass().getSimpleName(), model);
    }

    @Override
    DepartmentEntity toEntity(DepartmentModel model) {
        return new DepartmentEntity()
                .setId(model.id)
                .setName(model.name)
                .setAddress(model.address);
    }

    @Override
    DepartmentModel toModel(DepartmentEntity entity) {
        return new DepartmentModel()
                .setId(entity.id)
                .setName(entity.name)
                .setAddress(entity.address);
    }


    @GetMapping("/departments/{id}")
    public @ResponseBody Response getDepartmentById(@PathVariable("id") Long id) {
        return findById("getDepartmentById", id);
    }

    @GetMapping("/departments")
    public @ResponseBody Response getDepartments() {
        return findAll("getDepartments");
    }

    @PostMapping("/departments")
    public @ResponseBody Response persistOrUpdateDepartment(@RequestBody DepartmentModel model) {
        return persistOrUpdate("persistOrUpdateDepartment", model);
    }

    @DeleteMapping("/departments/{id}")
    public @ResponseBody Response deleteDepartmentById(@PathVariable("id") Long id) {
        return deleteById("deleteDepartmentById", id);
    }
}
