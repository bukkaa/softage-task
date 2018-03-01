package ru.softage.task.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkerModel extends AbstractModel {

    public String name;

    public String lastName;

    public String position;

    public Date hiringDate;

    public Date resignationDate;

    public Date birthday;

    public Long departmentId;


    public WorkerModel() {
    }

    public WorkerModel setId(Long id) {
        this.id = id;
        return this;
    }

    public WorkerModel setName(String name) {
        this.name = name;
        return this;
    }

    public WorkerModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public WorkerModel setPosition(String position) {
        this.position = position;
        return this;
    }

    public WorkerModel setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
        return this;
    }

    public WorkerModel setResignationDate(Date resignationDate) {
        this.resignationDate = resignationDate;
        return this;
    }

    public WorkerModel setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public WorkerModel setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", hiringDate=" + hiringDate +
                ", resignationDate=" + resignationDate +
                ", birthday=" + birthday +
                ", departmentId=" + departmentId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerModel worker = (WorkerModel) o;
        return Objects.equals(id, worker.id) &&
                Objects.equals(departmentId, worker.departmentId) &&
                Objects.equals(name, worker.name) &&
                Objects.equals(lastName, worker.lastName) &&
                Objects.equals(position, worker.position) &&
                Objects.equals(hiringDate, worker.hiringDate) &&
                Objects.equals(resignationDate, worker.resignationDate) &&
                Objects.equals(birthday, worker.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, position, hiringDate,
                resignationDate, birthday, departmentId);
    }
}
