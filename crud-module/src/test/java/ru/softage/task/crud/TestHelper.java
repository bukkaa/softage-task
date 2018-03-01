package ru.softage.task.crud;

import ru.softage.task.api.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHelper {

    public static void checkSuccess(Response response, String status) {
        assertThat(response).isNotNull();
        assertThat(response.code).isEqualTo(0);
        assertThat(response.entities).isNotNull();

        if (status != null) {
            assertThat(response.description).isEqualTo(status);
        }
    }
}
