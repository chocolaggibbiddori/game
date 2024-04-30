package chocola.chess.command;

import chocola.chess.ChessGame;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandFactory {

    private static final List<CommandFactory> FACTORY_LIST = new ArrayList<>();

    protected CommandFactory() {
    }

    public static void addFactory(CommandFactory commandFactory) {
        FACTORY_LIST.add(commandFactory);
    }

    public static Command createCommand(String input, ChessGame chessGame) {
        for (CommandFactory factory : FACTORY_LIST)
            if (factory.validate(input, chessGame))
                return factory.create(chessGame);

        throw new IllegalStateException();
    }

    protected abstract boolean validate(String input, ChessGame chessGame);

    protected abstract Command create(ChessGame chessGame);
}
