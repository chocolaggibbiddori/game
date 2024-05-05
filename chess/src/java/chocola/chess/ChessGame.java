package chocola.chess;

import chocola.chess.command.Command;
import chocola.chess.command.CommandFactory;
import chocola.chess.command.CommandFactoryConfiguration;
import chocola.common.IOProcessor;
import chocola.interfaces.Game;

import java.util.List;

public class ChessGame implements Game {

    private final ChessBoard board;
    private Team currentTeam;
    private Result result;

    public ChessGame() {
        board = new ChessBoard();
        currentTeam = Team.WHITE;
        result = Result.NONE;
    }

    static {
        CommandFactoryConfiguration.addFactories();
    }

    public ChessBoard.ChessValidator getValidator() {
        return board.getValidator();
    }

    @Override
    public void start() {
        IOProcessor.printStartMessage();
        IOProcessor.println("""
                            
                            기물을 움직이려면 기물의 타일과 움직일 타일을 입력해 주세요. ex) 'e2 e4'
                            기보를 보고싶으면 '기보'를 입력해주세요.
                            기권을 하고싶으면 '기권'을 입력해주세요.
                            
                            """ + board);

        while (result == Result.NONE) {
            turn();
        }
    }

    private void turn() {
        IOProcessor.println("%s 기물을 움직여주세요.".formatted(currentTeam));
        String input = IOProcessor.readLine();
        Command command = CommandFactory.createCommand(input, this);

        command.execute();
    }

    @Override
    public void end() {
        switch (result) {
            case WHITE_WIN -> IOProcessor.println("백 승리!!!\n게임을 종료합니다.");
            case BLACK_WIN -> IOProcessor.println("흑 승리!!!\n게임을 종료합니다.");
            case DRAW -> IOProcessor.printDrawMessage();
            default -> throw new IllegalStateException();
        }

        printNotation(result);
    }

    private void printNotation(Result result) {
        StringBuilder notationBuilder = createNotationBuilder();
        String resultNotation = Notation.getResultNotation(result).notate();
        notationBuilder.append(resultNotation);
        IOProcessor.println(notationBuilder.toString());
    }

    private void printNotation() {
        StringBuilder notationBuilder = createNotationBuilder();
        IOProcessor.println(notationBuilder.toString());
    }

    private StringBuilder createNotationBuilder() {
        StringBuilder notationSb = new StringBuilder();
        List<Notation> notationList = board.getNotationList();

        for (int i = 0, num = 1; i < notationList.size(); i += 2, num++) {
            Notation whiteNotation = notationList.get(i);
            Notation blackNotation = (i + 1 < notationList.size()) ? notationList.get(i + 1) : Notation.EMPTY;

            String notationStr = "%d. %s\t%s\n".formatted(num, whiteNotation, blackNotation);
            notationSb.append(notationStr);
        }

        return notationSb;
    }

    @Override
    public String getName() {
        return "chess";
    }

    public class MoveCommand implements Command {

        private final Tile target;
        private final Tile to;

        public MoveCommand(Tile target, Tile to) {
            this.target = target;
            this.to = to;
        }

        @Override
        public void execute() {
            board.move(target, to);
            IOProcessor.println(board.toString());

            currentTeam = currentTeam == Team.WHITE ? Team.BLACK : Team.WHITE;
            result = getValidator().getResult();
        }
    }

    public class GiveupCommand implements Command {

        @Override
        public void execute() {
            if (currentTeam == Team.WHITE) result = Result.BLACK_WIN;
            else result = Result.WHITE_WIN;
        }
    }

    public class NotationCommand implements Command {

        @Override
        public void execute() {
            printNotation();
        }
    }
}
