package mygame.game.chess.turn;

import mygame.turn.Notation;
import mygame.turn.Turn;
import org.springframework.stereotype.Component;

@Component
public class ChessTurn implements Turn {

    private int count;
    private String currentTeam;
    private Notation notation;

    @Override
    public void addCount() {
        count++;
    }

    @Override
    public String getCurrentTeam() {
        return currentTeam;
    }

    @Override
    public String getNotation() {
        return notation.getPiece() + " : " + notation.getStartPoint() + " -> " + notation.getEndPoint();
    }
}
