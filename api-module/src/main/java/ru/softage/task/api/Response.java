package ru.softage.task.api;

import ru.softage.task.api.models.AbstractModel;
import ru.softage.task.api.tools.CollectionUtils;

import java.io.Serializable;
import java.util.List;

import static java.util.Collections.emptyList;

public class Response<M extends AbstractModel> implements Serializable {

    public int code;

    public String description;

    public List<M> entities;


    public Response() {
        code = Code.OK;
        entities = emptyList();
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public Response setDescription(String description) {
        this.description = description;
        return this;
    }

    public Response setEntities(List<M> entities) {
        this.entities = entities;
        return this;
    }

    @Override
    public String toString() {
        return "{code=" + code +
                ", description='" + description + '\'' +
                ", entities=" + CollectionUtils.collectionToString(entities) +
                '}';
    }
}
