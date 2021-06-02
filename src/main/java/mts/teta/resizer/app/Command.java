package mts.teta.resizer.app;

import java.io.IOException;

@FunctionalInterface
public interface Command {
    void execute() throws IOException;
}
