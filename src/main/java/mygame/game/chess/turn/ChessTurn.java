package mygame.game.chess.turn;

import mygame.game.chess.name.Name;
import mygame.turn.Turn;
import org.springframework.stereotype.Component;

@Component
public class ChessTurn implements Turn {

    private int count;
    private String currentTeam;
    private ChessNotation notation;

    public void setNotation(ChessNotation notation) {
        this.notation = notation;
    }

    @Override
    public void turnStart() {
        count = 1;
        currentTeam = Name.TEAM_WHITE;
    }

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

    @Override
    public void changeTeam() {
        if (currentTeam.equals(Name.TEAM_WHITE)) {
            currentTeam = Name.TEAM_BLACK;
        } else {
            currentTeam = Name.TEAM_WHITE;
        }
    }

    public void nextTurn() {
        addCount();
        changeTeam();
        notation = null;
    }
}
