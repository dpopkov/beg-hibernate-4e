package learn.beghibernate.util.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Thing")
public class Thing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String name;
}
