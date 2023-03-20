package pro.karagodin.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import pro.karagodin.exceptions.CLIException;

public class WcCommand extends Command {

    private long lines = 0;
    private long words = 0;
    private long bytes = 0;

    /**
     * Displays number of lines count, words count and bytes cound in files from arguments or in {@code reader}
     * @param reader
     * @return
     * @throws CLIException
     */

    @Override
    public Reader run(Reader reader) throws CLIException {

        if (this.arguments.isEmpty()) {
            readFromIncomingReader(reader);
        } else {
            readFromArguments();
        }
        String output = lines + " " + words + " " + bytes;
        return new StringReader(output);
    }

    private void readFromIncomingReader(Reader reader) throws CLIException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            read(bufferedReader);
        } catch (Exception e) {
            throw new CLIException("Error while reading data", e);
        }
    }

    private void readFromArguments() throws CLIException {
        for (String fileName : arguments) {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
                read(fileReader);
            } catch (Exception e) {
                throw new CLIException("Exception happened wile reading file " + fileName, e);
            }
        }
    }

    private void read(BufferedReader br) throws IOException {
        String line = null;
        while ((line = br.readLine()) != null) {
            lines++;
            bytes += line.getBytes(StandardCharsets.UTF_8).length;
            words += line.split(" ").length;
        }
    }
}
