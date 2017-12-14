package learn.beghibernate.validated;

import learn.beghibernate.util.PersistHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CoordinateTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {1, 1},
                {1, 0},
                {-1, 1},
                {0, 1},
                {-1, 0},
                {0, -1},
                {1, -1},
                {0, 0}
        });
    }

    @Parameterized.Parameter(value = 0)
    public Integer x;
    @Parameterized.Parameter(value = 1)
    public Integer y;

    @Test
    public void testValidCoordinates() {
        Coordinate coordinate = Coordinate.builder().x(x).y(y).build();
        PersistHelper.persist(coordinate);
        assertNotNull(coordinate.getId());
    }
}