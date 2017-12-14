package learn.beghibernate.validated;

import learn.beghibernate.util.PersistHelper;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.fail;

public class InvalidCoordinateTest {
    @Test(expected = ConstraintViolationException.class)
    public void testInvalidCoordinates() {
        Coordinate coordinate = Coordinate.builder().x(-1).y(-1).build();
        PersistHelper.persist(coordinate);
        fail("Should have gotten a constraint violation");
    }
}
