package chocola.chess.command;

import chocola.common.IOProcessor;

class IllegalMoveCommand implements Command {

    @Override
    public void execute() {
        IOProcessor.println("해당 칸으로는 이동할 수 없습니다.");
    }
}
