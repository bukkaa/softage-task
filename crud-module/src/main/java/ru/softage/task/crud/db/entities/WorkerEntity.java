package ru.softage.task.crud.db.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

import static ru.softage.task.crud.db.entities.AbstractEntity.PARAMETER_ID;
import static ru.softage.task.crud.db.entities.WorkerEntity.DELETE_WORKER_BY_ID;
import static ru.softage.task.crud.db.entities.WorkerEntity.FIND_WORKERS;

@Entity(name = "workers")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = FIND_WORKERS,
                query = "SELECT * FROM workers",
                resultClass = WorkerEntity.class),
        @NamedNativeQuery(
                name = DELETE_WORKER_BY_ID,
                query = "DELETE FROM workers" +
                        " WHERE id = :" + PARAMETER_ID)
})
public class WorkerEntity implements AbstractEntity {
    public static final String FIND_WORKERS        = "FIND_WORKERS";
    public static final String DELETE_WORKER_BY_ID = "DELETE_WORKER_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    public String lastName;

    public String position;

    public Date hiringDate;

    public Date resignationDate;

    public Date birthday;

    @Column(name = "department_id")
    public Long departmentId;


    public WorkerEntity setId(long id) {
        this.id = id;
        return this;
    }

    public WorkerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public WorkerEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public WorkerEntity setPosition(String position) {
        this.position = position;
        return this;
    }

    public WorkerEntity setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
        return this;
    }

    public WorkerEntity setResignationDate(Date resignationDate) {
        this.resignationDate = resignationDate;
        return this;
    }

    public WorkerEntity setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public WorkerEntity setDepartmentId(long departmentId) {
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
                ", resignationDate=" + (resignationDate != null ? resignationDate : "---") +
                ", birthday=" + birthday +
                ", departmentId=" + departmentId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerEntity worker = (WorkerEntity) o;
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
