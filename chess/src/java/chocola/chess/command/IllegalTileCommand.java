package chocola.chess.command;

import chocola.common.IOProcessor;

class IllegalTileCommand implements Command {

    @Override
    public void execute() {
        IOProcessor.println("타일은 a1 ~ h8 내에서 입력해주세요.");
    }
}
