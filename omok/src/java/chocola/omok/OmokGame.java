package chocola.omok;

import chocola.interfaces.Game;
import chocola.common.InvalidInputException;
import chocola.interfaces.Point;

import static chocola.common.IOProcessor.*;

public class OmokGame implements Game {

    private final OmokBoard board;
    private Result result;
    private Team currentTeam = Team.BLACK;

    public OmokGame() {
        board = new OmokBoard();
        result = Result.NONE;
    }

    public void start() {
        printStartMessage();
        println(board.toString());
        play();
    }

    private void play() {
        while (result == Result.NONE)
            turn();
    }

    private void turn() {
        print("%s돌을 놓아주세요: ".formatted(currentTeam.teamName));

        OmokConverter converter = OmokConverter.getInstance();
        OmokValidator validator = new OmokValidator(board);

        try {
            Point point = readLine(validator, converter);
            if (currentTeam == Team.BLACK && !validator.isValidAtBlack(point)) {
                throw new InvalidInputException();
            }

            board.placeStone(currentTeam, point);
            println(board.toString());
            currentTeam = currentTeam.nextTurn();
            result = validator.getResult();
        } catch (InvalidInputException e) {
            println("해당 좌표에는 놓을 수 없습니다.");
        }
    }

    @Override
    public void end() {
        switch (result) {
            case BLACK_WIN -> {
                println("흑 승리!!!");
                printEndMessage();
            }
            case WHITE_WIN -> {
                println("백 승리!!!");
                printEndMessage();
            }
            case DRAW -> printDrawMessage();
            default -> throw new IllegalStateException("Unexpected value: " + result);
        }
    }

    @Override
    public String getName() {
        return "omok";
    }
}
