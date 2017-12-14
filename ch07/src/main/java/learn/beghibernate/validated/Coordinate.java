package learn.beghibernate.validated;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@NoQuadrantIII
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    @NotNull
    private Integer x;
    @Getter
    @Setter
    @NotNull
    private Integer y;
}
