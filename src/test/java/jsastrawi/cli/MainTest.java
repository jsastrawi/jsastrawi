package jsastrawi.cli;

import junit.framework.TestCase;
import static org.junit.Assert.*;

public class MainTest extends TestCase {

    public void testRemoveCommandFromArgs() {
        String[] args = new String[]{"arg1", "arg2"};
        String[] result = Main.removeCommandFromArgs(args);

        assertArrayEquals(result, new String[]{"arg2"});
    }

    public void testRemoveCommandFromArgsEmptyArray() {
        String[] args = new String[]{};
        String[] result = Main.removeCommandFromArgs(args);

        assertArrayEquals(result, new String[]{});
    }
}
