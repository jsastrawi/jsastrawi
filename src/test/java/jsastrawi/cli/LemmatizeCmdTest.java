package jsastrawi.cli;

import java.io.IOException;
import jsastrawi.cli.output.BufferedOutput;
import jsastrawi.cli.output.Output;
import junit.framework.TestCase;
import static org.junit.Assert.*;

public class LemmatizeCmdTest extends TestCase {

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

    public void testLemmatize() throws IOException {
        Output o = new BufferedOutput();
        LemmatizeCmd l = new LemmatizeCmd(o);

        l.run(new String[]{"menahan"});
        assertEquals("tahan\n", o.toString());
    }
}
