package chocola.chess.command;

public class CommandFactoryConfiguration {

    private CommandFactoryConfiguration() {
    }

    public static void addFactories() {
        CommandFactory.addFactory(new MoveCommandFactory());
        CommandFactory.addFactory(new GiveupCommandFactory());
        CommandFactory.addFactory(new NotationCommandFactory());
        CommandFactory.addFactory(new IllegalInputCommandFactory());
        CommandFactory.addFactory(new IllegalTileCommandFactory());
        CommandFactory.addFactory(new IllegalMoveCommandFactory());
    }
}
