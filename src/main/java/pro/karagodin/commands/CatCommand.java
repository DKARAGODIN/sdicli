package pro.karagodin.commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import pro.karagodin.exceptions.CLIException;

/**
 * TODO Implement custom reader to reduce memory usage
 */
public class CatCommand extends Command {
    @Override
    public Reader run(Reader reader) throws CLIException {
        if (this.arguments.isEmpty()) {
            return reader == null ? new StringReader("") : reader;
        } else {
            StringBuilder sb = new StringBuilder();
            for (String fileName : arguments) {
                try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
                    String line = null;
                    while ((line = fileReader.readLine()) != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                    }
                } catch (FileNotFoundException e) {
                    CLIException exception = new CLIException(fileName + ": No such file or directory");
                    exception.setNeedToPrintStackTrace(false);
                    throw exception;
                } catch (Exception e) {
                    throw new CLIException("Exception happened wile reading file " + fileName, e);
                }
            }
            StringReader outputReader = new StringReader(sb.toString());
            return outputReader;
        }
    }
}
