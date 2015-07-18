/**
 * JSastrawi is licensed under The MIT License (MIT)
 *
 * Copyright (c) 2015 Andy Librian
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package jsastrawi.cli;

import jsastrawi.cli.output.Output;
import jsastrawi.cli.output.BufferedOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The main class for Command Line Interface.
 */
public class Main {

    /**
     * The main entry point function which is called first from the CLI.
     *
     * @param args Command line arguments
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            printHelp();
        } else if (args[0].toLowerCase().equals("lemmatize")) {
            Output bufferedOutput = new BufferedOutput();
            LemmatizeCmd lemmatizeCmd = new LemmatizeCmd(bufferedOutput);
            lemmatizeCmd.handle(removeCommandFromArgs(args));
            System.out.print(bufferedOutput.toString());
        } else {
            printHelp();
        }
    }

    /**
     * Print Command Line usage
     */
    private static void printHelp() {
        System.out.println("JSastrawi");
        System.out.println("usage: command [arguments]\n");
        System.out.println("Available commands:");
        System.out.println("lemmatize            Determine the lemma (base form) for a given word.");
    }

    /**
     * Remove the command (which is the first argument) from an array of
     * arguments.
     *
     * @param args arguments
     * @return a new array of arguments after the command has been removed
     */
    static String[] removeCommandFromArgs(String[] args) {
        List<String> largs = new ArrayList<>(Arrays.asList(args));
        if (largs.size() > 0) {
            largs.remove(0);
        }
        return largs.toArray(new String[0]);
    }
}
