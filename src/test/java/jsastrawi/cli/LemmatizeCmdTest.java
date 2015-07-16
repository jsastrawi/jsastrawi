package jsastrawi.cli;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jsastrawi.cli.output.BufferedOutput;
import jsastrawi.cli.output.Output;
import jsastrawi.morphology.DefaultLemmatizer;
import jsastrawi.morphology.Lemmatizer;
import junit.framework.TestCase;
import static org.hamcrest.CoreMatchers.containsString;
import org.hamcrest.core.StringContains;
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

    public void testTestBed() {
        Output o = new BufferedOutput();
        LemmatizeCmd l = new LemmatizeCmd(o);

        Map<String, String> m = new HashMap<>();
        m.put("menahan", "tahan");
        m.put("memakan", "makan");

        Set<String> dictionary = new HashSet<>();
        dictionary.add("tahan");

        Lemmatizer lemmatizer = new DefaultLemmatizer(dictionary);

        l.runTestBed(m, lemmatizer);

        assertThat("Should contains total test summary", o.toString(), containsString("Total test : 2"));
        assertThat("Should contains success summary", o.toString(), containsString("Success : 1"));
        assertThat("Should contains failed summary", o.toString(), containsString("Failed : 1"));
        assertThat("Should contains success rate summary", o.toString(), containsString("Success rate : 50.0%"));
    }
}
