package mygame.game.chess.repository;

import mygame.game.chess.turn.ChessNotation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChessNotationRepository {

    private static final List<ChessNotation> notationList = new ArrayList<>();

    public void save(int turnNumber, ChessNotation notation) {
        notationList.add(turnNumber, notation);
    }

    public ChessNotation getNotation(int idx) {
        return notationList.get(idx);
    }
}
