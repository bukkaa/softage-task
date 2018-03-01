package ru.softage.task.crud.db.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

import static ru.softage.task.crud.db.entities.AbstractEntity.PARAMETER_ID;
import static ru.softage.task.crud.db.entities.WorkedHoursEntity.DELETE_WORKED_HOURS_BY_WORKER_ID;
import static ru.softage.task.crud.db.entities.WorkedHoursEntity.FIND_WORKED_HOURS;
import static ru.softage.task.crud.db.entities.WorkedHoursEntity.FIND_WORKED_HOURS_BY_WORKER_ID;

@Entity(name = "worked_hours")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = FIND_WORKED_HOURS,
                query = "SELECT * FROM worked_hours",
                resultClass = WorkedHoursEntity.class),
        @NamedNativeQuery(
                name = FIND_WORKED_HOURS_BY_WORKER_ID,
                query = "SELECT * FROM worked_hours" +
                        " WHERE worker_id = :" + PARAMETER_ID,
                resultClass = WorkedHoursEntity.class),
        @NamedNativeQuery(
                name = DELETE_WORKED_HOURS_BY_WORKER_ID,
                query = "DELETE FROM worked_hours" +
                        " WHERE worker_id = :" + PARAMETER_ID)
})
public class WorkedHoursEntity implements AbstractEntity {
    public static final String FIND_WORKED_HOURS                = "FIND_WORKED_HOURS";
    public static final String FIND_WORKED_HOURS_BY_WORKER_ID   = "FIND_WORKED_HOURS_BY_WORKER_ID";
    public static final String DELETE_WORKED_HOURS_BY_WORKER_ID = "DELETE_WORKED_HOURS_BY_WORKER_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Date startDate;

    public Date finishDate;

    @Column(name = "worker_id")
    public long workerId;


    public WorkedHoursEntity() {
    }

    public WorkedHoursEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public WorkedHoursEntity setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public WorkedHoursEntity setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public WorkedHoursEntity setWorkerId(long workerId) {
        this.workerId = workerId;
        return this;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", workerId=" + workerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkedHoursEntity that = (WorkedHoursEntity) o;
        return Objects.equals(id, that.id) &&
                workerId == that.workerId &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(finishDate, that.finishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, finishDate, workerId);
    }
}
