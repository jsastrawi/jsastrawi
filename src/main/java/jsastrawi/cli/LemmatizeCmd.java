package jsastrawi.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsastrawi.cli.output.Output;
import jsastrawi.cli.output.SystemOutput;
import jsastrawi.morphology.DefaultLemmatizer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class LemmatizeCmd {

    private Output output;
    
    public LemmatizeCmd(Output output) {
        this.output = output;
    }
    
    public LemmatizeCmd() {
        this.output = new SystemOutput();
    }
    
    public Output getOutput() {
        return output;
    }

    public void run(String[] args) throws IOException {
        Options options = buildOptions();
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (args.length == 0 || cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("lemmatize [args...] WORD...", options);
            } else if (!cmd.getArgList().isEmpty()) {
                Set<String> dictionary;
                
                if (cmd.hasOption('d')) {
                    dictionary = getDictionaryFromFile(cmd.getOptionValue('d'));
                } else {
                    dictionary = getDefaultDictionary();
                }

                jsastrawi.morphology.Lemmatizer l = new DefaultLemmatizer(dictionary);

                for (String word : cmd.getArgs()) {
                    output.println(l.lemmatize(word));
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
        options.addOption(Option.builder("w")
                .longOpt("word")
                .desc("The word to find the lemma, e.g: bertahan.")
                .hasArg()
                .argName("WORD")
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
}
