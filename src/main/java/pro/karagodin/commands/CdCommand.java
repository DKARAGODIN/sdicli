package pro.karagodin.commands;

import pro.karagodin.Environment;
import pro.karagodin.exceptions.CLIException;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.Objects;

public class CdCommand extends Command {

    @Override
    public Reader run(Reader reader) throws CLIException {

        if (this.arguments.size() == 0) {
            Environment.setVariable("PWD", "/home/" + Environment.getVariableValue("USER"));
        } else if (this.arguments.size() == 1) {

            if (this.arguments.get(0).equals("~")) {
                this.arguments.set(0, "/home/" + Environment.getVariableValue("USER"));
            }

            var path = Paths.get(
                    Environment.getVariableValue("PWD"),
                    this.arguments.get(0)
            ).normalize();

            if (!Files.exists(path)) { path = Path.of(this.arguments.get(0)); }

            if (Files.exists(path) && Files.isDirectory(path) ) {
                Environment.setVariable("PWD", path.toString());
            } else {
                throw new CLIException("Now such directory");
            }

        } else {
            throw new CLIException("Unexpected number of arguments");
        }

        return null;
    }
}
