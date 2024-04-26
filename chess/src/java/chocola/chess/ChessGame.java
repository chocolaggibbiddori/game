package chocola.chess;

import chocola.common.IOProcessor;
import chocola.interfaces.Game;

import java.util.List;

public class ChessGame implements Game {

    private final ChessBoard board;
    private final ChessValidator validator;
    private Team currentTeam;
    private Result result;

    public ChessGame() {
        board = new ChessBoard();
        validator = new ChessValidator(board);
        currentTeam = Team.WHITE;
        result = Result.NONE;
    }

    ChessValidator getValidator() {
        return validator;
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

        printNotation();
    }

    private void printNotation() {
        StringBuilder notationSb = new StringBuilder();
        List<Notation> notationList = board.getNotationList();

        for (int i = 0, num = 1; i < notationList.size(); i += 2, num++) {
            Notation whiteNotation = notationList.get(i);
            Notation blackNotation = (i + 1 < notationList.size()) ? notationList.get(i + 1) : Notation.EMPTY;

            String notationStr = "%d. %s\t%s\n".formatted(num, whiteNotation, blackNotation);
            notationSb.append(notationStr);
        }

        IOProcessor.println(notationSb.toString());
    }

    @Override
    public String getName() {
        return "chess";
    }

    static class IllegalCommand implements Command {

        private final Type type;

        IllegalCommand(Type type) {
            this.type = type;
        }

        @Override
        public void execute() {
            String message = switch (type) {
                case INPUT -> "입력은 '(기물의 타일) (이동할 타일)' 형식에 맞춰 해주세요.";
                case TILE -> "타일은 a1 ~ h8 내에서 입력해주세요.";
                case MOVE -> "해당 칸으로는 이동할 수 없습니다.";
            };

            IOProcessor.println(message);
        }

        enum Type {
            INPUT, TILE, MOVE
        }
    }

    class MoveCommand implements Command {

        private final Tile target;
        private final Tile to;

        MoveCommand(Tile target, Tile to) {
            this.target = target;
            this.to = to;
        }

        @Override
        public void execute() {
            board.move(target, to);
            IOProcessor.println(board.toString());

            currentTeam = currentTeam == Team.WHITE ? Team.BLACK : Team.WHITE;
            result = validator.getResult();
        }
    }

    class GiveupCommand implements Command {

        @Override
        public void execute() {
            if (currentTeam == Team.WHITE) result = Result.BLACK_WIN;
            else result = Result.WHITE_WIN;
        }
    }

    class NotationCommand implements Command {

        @Override
        public void execute() {
            printNotation();
        }
    }
}
