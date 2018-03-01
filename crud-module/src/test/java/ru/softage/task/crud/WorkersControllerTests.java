package ru.softage.task.crud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.softage.task.api.Response;
import ru.softage.task.api.models.WorkerModel;
import ru.softage.task.crud.controllers.WorkersController;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.softage.task.crud.TestHelper.checkSuccess;

/**
 * Some few entries were inserted by Spring from resources/data.sql during launching.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WorkersControllerTests {

	private static final String URL = "http://localhost:8080/workers";

	@Autowired
	private TestRestTemplate rest;

	@Autowired
	private WorkersController controller;


    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

	@Test
	public void createNewWorker() {
        WorkerModel model = new WorkerModel()
                            .setId(10L)
                            .setName("TEST")
                            .setLastName("WORKER")
                            .setPosition("TEST POSITION")
                            .setBirthday(Date.from(Instant.parse("1000-01-01T11:00:00Z")))
                            .setHiringDate(Date.from(Instant.parse("2000-01-01T11:00:00Z")))
                            .setDepartmentId(2L);

        Response<WorkerModel> response = rest.postForObject(URL, model, Response.class);

        checkSuccess(response, "updated");

        assertThat(response.entities.size()).isEqualTo(1);
        WorkerModel entity = response.entities.get(0);

        assertThat(entity).isNotNull();
        assertThat(entity.id).isNotNull();
        assertThat(entity.name).isEqualTo(model.name);
        assertThat(entity.lastName).isEqualTo(model.lastName);
        assertThat(entity.position).isEqualTo(model.position);
        assertThat(entity.departmentId).isEqualTo(model.departmentId);
        assertThat(entity.birthday).isEqualTo(model.birthday);
        assertThat(entity.hiringDate).isEqualTo(model.hiringDate);
        assertThat(entity.resignationDate).isEqualTo(model.resignationDate);
	}

	@Test
	public void getExistingWorkerById() {
        Long deptId = 1L;

        Response<WorkerModel> response = rest.getForObject(URL + "/" + deptId, Response.class);

        checkSuccess(response, null);

        assertThat(response.entities.size()).isEqualTo(1);
        WorkerModel entity = response.entities.get(0);

        assertThat(entity).isNotNull();
        assertThat(entity.id).isNotNull();
        assertThat(entity.name).isEqualTo("Denis");
        assertThat(entity.lastName).isEqualTo("Medvedev");
        assertThat(entity.position).isEqualTo("Developer");
        assertThat(entity.departmentId).isEqualTo(3);
        assertThat(entity.birthday).isEqualTo(Date.from(Instant.parse("1992-03-18T01:30:00Z")));
        assertThat(entity.hiringDate).isEqualTo(Date.from(Instant.parse("2016-12-09T09:00:00Z")));
        assertThat(entity.resignationDate).isEqualTo(Date.from(Instant.parse("2018-03-12T09:00:00Z")));
    }

    @Test
    public void getAllExistingWorkers() {
        Response<WorkerModel> response = rest.getForObject(URL, Response.class);

        checkSuccess(response, null);

        assertThat(response.entities).isNotEmpty();
    }

    @Test
    public void updateWorker() {
        WorkerModel model = new WorkerModel()
                .setId(2L)
                .setName("John")
                .setLastName("Diggle")
                .setPosition("UPDATED POSITION")
                .setBirthday(Date.from(Instant.parse("1980-10-11T12:30:00Z")))
                .setHiringDate(Date.from(Instant.parse("2010-01-09T09:00:00Z")))
                .setResignationDate(Date.from(Instant.parse("2018-01-01T11:00:00Z")))
                .setDepartmentId(2L);

        Response<WorkerModel> response = rest.postForObject(URL, model, Response.class);

        checkSuccess(response, "updated");

        assertThat(response.entities.size()).isEqualTo(1);
        WorkerModel entity = response.entities.get(0);

        assertThat(entity).isNotNull();
        assertThat(entity.id).isNotNull();
        assertThat(entity.name).isEqualTo("John");
        assertThat(entity.lastName).isEqualTo("Diggle");
        assertThat(entity.position).isEqualTo("UPDATED POSITION");
        assertThat(entity.departmentId).isEqualTo(2);
        assertThat(entity.birthday).isEqualTo(Date.from(Instant.parse("1980-10-11T12:30:00Z")));
        assertThat(entity.hiringDate).isEqualTo(Date.from(Instant.parse("2010-01-09T09:00:00Z")));
        assertThat(entity.resignationDate).isEqualTo(Date.from(Instant.parse("2018-01-01T11:00:00Z")));
    }

    @Test
    public void deleteWorker() {
        Long deptId = 3L;

        Response<WorkerModel> response = rest.getForObject(URL + "/" + deptId, Response.class);

        checkSuccess(response, null);

        assertThat(response.entities.size()).isEqualTo(1);
        WorkerModel entity = response.entities.get(0);

        assertThat(entity).isNotNull();
        assertThat(entity.id).isEqualTo(deptId);
        assertThat(entity.name).isEqualTo("Oliver");
        assertThat(entity.lastName).isEqualTo("Queen");

        rest.delete(URL + "/" + deptId);

        response = rest.getForObject(URL + "/" + deptId, Response.class);
        checkSuccess(response, "no result");
        assertThat(response.entities).isEmpty();
    }
}
