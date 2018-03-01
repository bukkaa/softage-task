package ru.softage.task.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentModel extends AbstractModel {

    public String name;

    public String address;


    public DepartmentModel() {
    }

    public DepartmentModel setId(Long id) {
        this.id = id;
        return this;
    }

    public DepartmentModel setName(String name) {
        this.name = name;
        return this;
    }

    public DepartmentModel setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentModel that = (DepartmentModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
