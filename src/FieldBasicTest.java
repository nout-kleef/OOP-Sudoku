import org.junit.Test;
import org.junit.Before;

public class FieldBasicTest {

    @Test
    public void noArgCtorTest() {
        Field f = new Field();
    }

    @Test
    public void ctorTest() {
        Field f = new Field(1, false);
    }

    @Test
    public void getValueTest() {
        Field f = new Field(1, false);
        int result = f.getValue();
    }

    @Test
    public void setValueTest() {
        Field f = new Field(1, false);
        f.setValue(5);
    }

    @Test
    public void isInitialTest() {
        Field f = new Field(1, false);
        boolean result = f.isInitial();
    }

    @Test
    public void toStringTest() {
        Field f = new Field(1, false);
        String result = f.toString();
    }
}
