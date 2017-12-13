package learn.beghibernate.validated;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class ValidatedSimplePerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    @Size(min = 2, max = 60)
    private String fname;
    @Column
    @NotNull
    @Size(min = 2, max = 60)
    private String lname;
    @Column
    @Min(value = 13)
    private Integer age;
}
