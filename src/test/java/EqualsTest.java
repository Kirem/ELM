import org.junit.Test;

import pl.edu.pwr.elm.Output;

import static junit.framework.TestCase.assertEquals;

public class EqualsTest {
    public static final String TAG = EqualsTest.class.getSimpleName();

    public EqualsTest() {
    }

    @Test
    public void testArraysEquals(){
        assertEquals(new Output(new double[]{1, 0, 0}), new Output(new double[]{1, 0, 0}));
    }

    @Test
    public void testFloatingToInteger(){
        assertEquals(1.3, 1);
    }
    @Test
    public void testIntToInteger(){
        assert((int)1.5== 1.0);
    }
}
