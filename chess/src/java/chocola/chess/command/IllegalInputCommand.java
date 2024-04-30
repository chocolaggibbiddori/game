package chocola.chess.command;

import chocola.common.IOProcessor;

class IllegalInputCommand implements Command {

    @Override
    public void execute() {
        IOProcessor.println("입력은 '(기물의 타일) (이동할 타일)' 형식에 맞춰 해주세요.");
    }
}
