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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsastrawi.cli.output.Output;
import jsastrawi.cli.output.SystemOutput;
import jsastrawi.morphology.DefaultLemmatizer;
import jsastrawi.morphology.Lemmatizer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Handler of lemmatize command
 */
public class LemmatizeCmd {

    private final Output output;

    /**
     * Constructor
     *
     * @param output Output object. It is used to print messages.
     */
    public LemmatizeCmd(Output output) {
        this.output = output;
    }

    /**
     * Constructor
     */
    public LemmatizeCmd() {
        this.output = new SystemOutput();
    }

    /**
     * @return output object.
     */
    public Output getOutput() {
        return output;
    }

    /**
     * Handle lemmatize command
     *
     * @param args arguments
     * @throws IOException IOException
     */
    public void handle(String[] args) throws IOException {
        Options options = buildOptions();
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (args.length == 0 || cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("lemmatize [args...] WORD...", options);
            } else {
                Set<String> dictionary;

                if (cmd.hasOption('d')) {
                    dictionary = getDictionaryFromFile(cmd.getOptionValue('d'));
                } else {
                    dictionary = getDefaultDictionary();
                }

                Lemmatizer l = new DefaultLemmatizer(dictionary);

                if (cmd.hasOption("tb")) {
                    Map<String, String> map = scanTestBedMapFromFile(cmd.getOptionValue("tb"));
                    runTestBed(map, l);
                } else if (!cmd.getArgList().isEmpty()) {
                    for (String word : cmd.getArgs()) {
                        output.println(l.lemmatize(word));
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(LemmatizeCmd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Options buildOptions() {
        Options options = new Options();
        options.addOption(Option.builder("d")
                .longOpt("dictionary")
                .desc("The path of dictionary file. If not specified, the default dictionary will be used.")
                .hasArg()
                .argName("FILE")
                .build());
        options.addOption(Option.builder("tb")
                .longOpt("testbed")
                .desc("Run a testbed against a csv file. The expected format is word,lemma.")
                .hasArg()
                .argName("FILE")
                .build());
        options.addOption("h", "help", false, "This help.");

        return options;
    }

    private Set<String> getDictionaryFromFile(String file) throws FileNotFoundException, IOException {
        Set<String> dictionary = new HashSet<>();
        File f = new File(file);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        fillSet(dictionary, br);

        return dictionary;
    }

    private Set<String> getDefaultDictionary() throws IOException {
        Set<String> dictionary = new HashSet<>();
        InputStream in = LemmatizeCmd.class.getResourceAsStream("/root-words.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        fillSet(dictionary, br);

        return dictionary;
    }

    private void fillSet(Set<String> set, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            set.add(line);
        }
    }

    void runTestBed(Map<String, String> testbed, Lemmatizer lemmatizer) {
        int successCount = 0;
        int failedCount = 0;
        float successRate = 0;
        Map<String, String> failures = new HashMap<>();
        Map<String, String> actuals = new HashMap<>();

        for (Map.Entry<String, String> entry : testbed.entrySet()) {
            String lemma = lemmatizer.lemmatize(entry.getKey());
            if (lemma.equals(entry.getValue())) {
                successCount++;
            } else {
                failedCount++;
                failures.put(entry.getKey(), entry.getValue());
                actuals.put(entry.getKey(), lemma);
            }
        }

        if (testbed.size() > 0) {
            successRate = (float) successCount * 100 / testbed.size();
        }

        output.println("Total test : " + testbed.size());
        output.println("Success : " + successCount);
        output.println("Failed : " + failedCount);
        output.println("Success rate : " + successRate + "%");

        if (failedCount > 0) {
            output.println("Failures:");
            int idx = 0;
            for (Map.Entry<String, String> entry : failures.entrySet()) {

                output.println("[" + idx + "] word: " + entry.getKey() + ", expected: " + entry.getValue() + ", actual: " + actuals.get(entry.getKey()));
                idx++;
            }
        }
    }

    private Map<String, String> scanTestBedMapFromFile(String filePath) throws FileNotFoundException, IOException {
        Map<String, String> map = new HashMap<>();

        File f = new File(filePath);
        FileReader fr = new FileReader(f);
        BufferedReader reader = new BufferedReader(fr);

        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            if (split.length >= 2) {
                map.put(split[0], split[1]);
            }
        }

        return map;
    }
}
