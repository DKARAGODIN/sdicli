package pro.karagodin.commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.karagodin.exceptions.CLIException;

public class GrepCommand extends Command {

    private Searcher searcher = new Searcher();
    private List<String> fileNames = new ArrayList<>();

    @Override
    public Reader run(Reader reader) throws CLIException {
        if (arguments.isEmpty()) {
            return reader == null ? new StringReader("") : reader;
        }

        parseArguments();

        StringBuilder sb = new StringBuilder();
        if (fileNames.isEmpty()) {
            if (reader == null)
                return new StringReader("");

            try (BufferedReader br = new BufferedReader(reader)) {
                sb.append(read(br));
            } catch (IOException e) {
                throw new CLIException("Exception happened wile reading from inpput reader", e);
            }
        } else {
            for (String fileName : fileNames) {
                try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
                    sb.append(read(fileReader));
                } catch (FileNotFoundException e) {
                    CLIException exception = new CLIException(fileName + ": No such file or directory");
                    exception.setNeedToPrintStackTrace(false);
                    throw exception;
                } catch (Exception e) {
                    throw new CLIException("Exception happened wile reading file " + fileName, e);
                }
            }
        }
        return new StringReader(sb.toString());
    }

    private String read(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = null;
        int printLines = 0;
        Pattern regexPattern = Pattern.compile(searcher.pattern);
        while ((line = reader.readLine()) != null) {
            switch (searcher.searcherType) {
                case A:
                case R:
                    Matcher matcher = regexPattern.matcher(line);
                    if (matcher.find()) {
                        printLines = searcher.followUpLines;
                        sb.append(line);
                        sb.append("\n");
                    } else {
                        if (printLines > 0) {
                            sb.append(line);
                            printLines--;
                            sb.append("\n");
                        }
                    }
                    break;
                case I:
                    if (line.toUpperCase().contains(searcher.pattern.toUpperCase())) {
                        sb.append(line);
                        sb.append("\n");
                    }
                    break;
                case W:
                    if (line.trim().equals(searcher.pattern)) {
                        sb.append(line);
                        sb.append("\n");
                    }
                    break;
                default:
                    throw new RuntimeException("Error happened in Grep function");
            }
        }
        return sb.toString();
    }

    private void parseArguments() throws CLIException {
        String first = arguments.remove(0);
        if (first.startsWith("-")) {
            if (first.length() != 2)
                throw new CLIException("Invalid argument " + first);
            char arg = first.charAt(1);
            switch (arg) {
                case 'w':
                    searcher.searcherType = SearcherType.W;
                    break;
                case 'i':
                    searcher.searcherType = SearcherType.I;
                    break;
                case 'A':
                    searcher.searcherType = SearcherType.A;
                    if (arguments.isEmpty())
                        throw new CLIException("Invalid argument option");
                    String second = arguments.remove(0);
                    try {
                        int printLines = Integer.parseInt(second);
                        if (printLines < 0)
                            throw new CLIException("Invalid argument option");
                        searcher.followUpLines = printLines;
                    } catch (NumberFormatException e) {
                        throw new CLIException("Invalid argument option");
                    }
                    break;
                default:
                    throw new CLIException("Invalid argument " + first);
            }
        }
        if (searcher.searcherType == null)
            searcher.searcherType = SearcherType.R;

        if (searcher.searcherType == SearcherType.R) {
            searcher.pattern = first;
        } else {
            if (arguments.isEmpty())
                throw new CLIException("Invalid arguments");

            String pattern = arguments.remove(0);
            searcher.pattern = pattern;
        }

        for (String fileName : arguments) {
            fileNames.add(fileName);
        }
    }

    private static class Searcher {
        public SearcherType searcherType;
        public String pattern;
        public int followUpLines;
    }

    private enum SearcherType {
        I, W, A, R,
    }
}
