package ru.softage.task.crud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.softage.task.api.Response;
import ru.softage.task.api.models.DepartmentModel;
import ru.softage.task.crud.controllers.DepartmentsController;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.softage.task.crud.TestHelper.checkSuccess;

/**
 * Some few entries were inserted by Spring from resources/data.sql during launching.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DepartmentsControllerTests {

	private static final String URL = "http://localhost:8080/departments";

	@Autowired
	private TestRestTemplate rest;

	@Autowired
	private DepartmentsController controller;


    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

	@Test
	public void createNewDept() {
        DepartmentModel model = new DepartmentModel()
                                .setName("TEST DEPT")
                                .setAddress("TEST DEPT ADDRESS");

		Response<DepartmentModel> response = rest.postForObject(URL, model, Response.class);

        checkSuccess(response, "created");

		assertThat(response.entities.size()).isEqualTo(1);
        DepartmentModel entity = response.entities.get(0);

        assertThat(entity).isNotNull();
        assertThat(entity.id).isNotNull();
        assertThat(entity.name).isEqualTo(model.name);
        assertThat(entity.address).isEqualTo(model.address);
	}

	@Test
	public void getExistingDeptById() {
        Long deptId = 1L;

        Response<DepartmentModel> response = rest.getForObject(URL + "/" + deptId, Response.class);

        checkSuccess(response, null);

        assertThat(response.entities.size()).isEqualTo(1);
        DepartmentModel entity = response.entities.get(0);

        assertThat(entity).isNotNull();
        assertThat(entity.id).isEqualTo(deptId);
        assertThat(entity.name).isEqualTo("White House");
        assertThat(entity.address).isEqualTo("Address of Dept_1");
    }

    @Test
    public void getAllExistingDepts() {
        Response<DepartmentModel> response = rest.getForObject(URL, Response.class);

        checkSuccess(response, null);

        assertThat(response.entities).isNotEmpty();
    }

    @Test
    public void updateDept() {
        DepartmentModel model = new DepartmentModel()
                                .setId(2L)
                                .setName("UPD TEST DEPT")
                                .setAddress("UPD DEPT ADDRESS");

        Response<DepartmentModel> response = rest.postForObject(URL, model, Response.class);

        checkSuccess(response, "updated");

        assertThat(response.entities.size()).isEqualTo(1);
        DepartmentModel entity = response.entities.get(0);

        assertThat(entity).isNotNull();
        assertThat(entity.id).isEqualTo(2L);
        assertThat(entity.name).isEqualTo(model.name);
        assertThat(entity.address).isEqualTo(model.address);
    }

    @Test
    public void deleteDept() {
        Long deptId = 3L;

        Response<DepartmentModel> response = rest.getForObject(URL + "/" + deptId, Response.class);

        checkSuccess(response, null);

        assertThat(response.entities.size()).isEqualTo(1);
        DepartmentModel entity = response.entities.get(0);

        assertThat(entity).isNotNull();
        assertThat(entity.id).isEqualTo(deptId);
        assertThat(entity.name).isEqualTo("NASA");
        assertThat(entity.address).isEqualTo("USA, Washington D.C.");

        rest.delete(URL + "/" + deptId);

        response = rest.getForObject(URL + "/" + deptId, Response.class);
        checkSuccess(response, "no result");
        assertThat(response.entities).isEmpty();
    }
}
