package pro.karagodin.commands;

import pro.karagodin.Environment;
import pro.karagodin.exceptions.CLIException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class LsCommand extends Command {

    @Override
    public Reader run(Reader reader) throws CLIException {

        if (this.arguments.size() > 1) {
            throw new CLIException("Unexpected number of arguments");
        }

        var path = Path.of(Environment.getVariableValue("PWD"));

        if (this.arguments.size() == 1) {

            if (this.arguments.get(0).equals("~")) {
                this.arguments.set(0, "/home/" + Environment.getVariableValue("USER"));
            }

            path = Paths.get(
                    Environment.getVariableValue("PWD"),
                    this.arguments.get(0)
            ).normalize();

            if (!Files.exists(path)) { path = Path.of(this.arguments.get(0)); }
        }

        if (!Files.exists(path)) {
            throw new CLIException("No such file or directory");
        }

        if (Files.isDirectory(path)) {
            try {
                return new StringReader(
                    Files.list(path)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .filter(p -> !p.startsWith("."))
                        .collect(Collectors.joining(" "))
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new StringReader( path.getFileName().toString() );
        }
    }
}
