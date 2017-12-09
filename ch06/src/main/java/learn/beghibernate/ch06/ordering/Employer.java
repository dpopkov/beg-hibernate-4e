package learn.beghibernate.ch06.ordering;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @OrderColumn (name = "employee_number")
    private List<Employee> employees = new ArrayList<>();

    public void addEmployeeByName(String name) {
        Employee emp1 = new Employee();
        emp1.setName(name);
        employees.add(emp1);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                '}';
    }
}
