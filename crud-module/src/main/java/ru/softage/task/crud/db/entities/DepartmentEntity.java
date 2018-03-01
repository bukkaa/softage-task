package ru.softage.task.crud.db.entities;

import javax.persistence.*;
import java.util.Objects;

import static ru.softage.task.crud.db.entities.AbstractEntity.PARAMETER_ID;
import static ru.softage.task.crud.db.entities.DepartmentEntity.DELETE_DEPT_BY_ID;
import static ru.softage.task.crud.db.entities.DepartmentEntity.FIND_DEPARTMENTS;

@Entity(name = "departments")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = FIND_DEPARTMENTS,
                query = "SELECT * FROM departments",
                resultClass = DepartmentEntity.class),
        @NamedNativeQuery(
                name = DELETE_DEPT_BY_ID,
                query = "DELETE FROM departments" +
                        " WHERE id = :" + PARAMETER_ID)
})
public class DepartmentEntity implements AbstractEntity {
    public static final String FIND_DEPARTMENTS  = "FIND_DEPARTMENTS";
    public static final String DELETE_DEPT_BY_ID = "DELETE_DEPT_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    public String address;


    public DepartmentEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public DepartmentEntity setName(String name) {
        this.name = name;
        return this;
    }

    public DepartmentEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentEntity that = (DepartmentEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }
}
