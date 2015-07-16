package jsastrawi.cli;

import jsastrawi.cli.output.Output;
import jsastrawi.cli.output.BufferedOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            printHelp();
        } else if (args[0].toLowerCase().equals("lemmatize")) {
            Output bufferedOutput = new BufferedOutput();
            LemmatizeCmd lemmatizeCmd = new LemmatizeCmd(bufferedOutput);
            lemmatizeCmd.run(removeCommandFromArgs(args));
            System.out.print(bufferedOutput.toString());
        } else {
            printHelp();
        }
    }

    private static void printHelp() {
        System.out.println("JSastrawi");
        System.out.println("usage: command [arguments]\n");
        System.out.println("Available commands:");
        System.out.println("lemmatize            Determine the lemma (base form) for a given word.");
    }
    
    static String[] removeCommandFromArgs(String[] args) {
        List<String> largs = new ArrayList<>(Arrays.asList(args));
        if (largs.size() > 0) {
            largs.remove(0);
        }
        return largs.toArray(new String[0]);
    }
}
