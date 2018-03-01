package ru.softage.task.api.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DepartmentModel.class),
        @JsonSubTypes.Type(value = WorkedHoursModel.class),
        @JsonSubTypes.Type(value = WorkerModel.class)
})
public abstract class AbstractModel implements Serializable {
    public Long id;

    public AbstractModel() {
    }
}
