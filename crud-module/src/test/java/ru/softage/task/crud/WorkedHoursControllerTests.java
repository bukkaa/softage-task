package ru.softage.task.crud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.softage.task.api.Response;
import ru.softage.task.api.models.WorkedHoursModel;
import ru.softage.task.crud.controllers.WorkedHoursController;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.softage.task.crud.TestHelper.checkSuccess;

/**
 * Some few entries were inserted by Spring from resources/data.sql during launching.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WorkedHoursControllerTests {

	private static final String URL = "http://localhost:8080/worked_hours";

	@Autowired
	private TestRestTemplate rest;

	@Autowired
	private WorkedHoursController controller;


    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

	@Test
	public void createNew() {
        WorkedHoursModel model = new WorkedHoursModel()
                                .setWorkerId(2L)
                                .setStartDate (Date.from(Instant.parse("2000-01-01T11:00:00Z")))
                                .setFinishDate(Date.from(Instant.parse("2000-01-01T12:00:00Z")));

        Response<WorkedHoursModel> response = rest.postForObject(URL, model, Response.class);

        checkSuccess(response, "created");

        assertThat(response.entities.size()).isEqualTo(1);
        WorkedHoursModel entity = response.entities.get(0);

        assertThat(entity).isNotNull();
        assertThat(entity.id).isNotNull();
        assertThat(entity.workerId).isEqualTo(model.workerId);
        assertThat(entity.startDate).isEqualTo(model.startDate);
        assertThat(entity.finishDate).isEqualTo(model.finishDate);
	}

	@Test
	public void getExistingHoursByWorkerId() {
        Long workerId = 1L;
        Response<WorkedHoursModel> response = rest.getForObject(URL + "/" + workerId, Response.class);

        checkSuccess(response, null);

        assertThat(response.entities).isNotEmpty();
        WorkedHoursModel entity = response.entities.get(0);

        assertThat(entity.id).isEqualTo(1L);
        assertThat(entity.workerId).isEqualTo(workerId);
        assertThat(entity.startDate).isEqualTo(Date.from(Instant.parse("2018-02-17T09:00:00Z")));
        assertThat(entity.finishDate).isEqualTo(Date.from(Instant.parse("2018-02-17T23:00:00Z")));
    }

    @Test
    public void getAllExistingHoursRecs() {
        Response<WorkedHoursModel> response = rest.getForObject(URL, Response.class);

        checkSuccess(response, null);

        assertThat(response.entities).isNotEmpty();
    }

    @Test
    public void update() {
        WorkedHoursModel model = new WorkedHoursModel()
                                    .setId(2L)
                                    .setWorkerId(2L)
                                    .setStartDate (Date.from(Instant.parse("2111-01-01T11:00:00Z")))
                                    .setFinishDate(Date.from(Instant.parse("2111-01-01T12:00:00Z")));

        Response<WorkedHoursModel> response = rest.postForObject(URL, model, Response.class);

        checkSuccess(response, "updated");

        assertThat(response.entities.size()).isEqualTo(1);
        WorkedHoursModel entity = response.entities.get(0);

        assertThat(entity).isNotNull();
        assertThat(entity.id).isEqualTo(model.id);
        assertThat(entity.workerId).isEqualTo(model.workerId);
        assertThat(entity.startDate).isEqualTo(model.startDate);
        assertThat(entity.finishDate).isEqualTo(model.finishDate);
    }

    @Test
    public void delete() {
        Long workerId = 3L;

        WorkedHoursModel model = new WorkedHoursModel()
                .setWorkerId(workerId)
                .setStartDate (Date.from(Instant.parse("2000-01-01T11:00:00Z")))
                .setFinishDate(Date.from(Instant.parse("2000-01-01T12:00:00Z")));

        Response<WorkedHoursModel> response = rest.postForObject(URL, model, Response.class);
        checkSuccess(response, "created");

        rest.delete(URL + "/" + workerId);

        response = rest.getForObject(URL + "/" + workerId, Response.class);
        assertThat(response.description).isEqualTo("no result");
        assertThat(response.entities).isEmpty();
    }
}
