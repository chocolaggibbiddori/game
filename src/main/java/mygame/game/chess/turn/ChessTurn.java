package mygame.game.chess.turn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mygame.game.chess.name.Name;
import mygame.game.chess.repository.ChessNotationRepository;
import mygame.piece.Piece;
import mygame.point.Point;
import mygame.turn.Turn;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class ChessTurn implements Turn {

    private final ChessNotationRepository repository;

    private int count;
    private String currentTeam;
    private ChessNotation notation;

    public ChessNotation setNotation(Piece piece, Point startPoint, Point endPoint) {
        notation.setPiece(piece);
        notation.setStartPoint(startPoint);
        notation.setEndPoint(endPoint);
        return notation;
    }

    @Override
    public void turnStart() {
        count = 1;
        currentTeam = Name.TEAM_WHITE;
        notation = new ChessNotation();
        repository.save(0, null);
    }

    @Override
    public void addCount() {
        count++;
    }

    @Override
    public String getStringNotation() {
        if (notation.getPiece() == null) return "";
        return notation.getPiece() + " " + notation.getStartPoint() + " -> " + notation.getEndPoint();
    }

    @Override
    public void changeTeam() {
        if (currentTeam.equals(Name.TEAM_WHITE)) {
            currentTeam = Name.TEAM_BLACK;
        } else {
            currentTeam = Name.TEAM_WHITE;
        }
    }

    @Override
    public void nextTurn() {
        repository.save(count, notation);
        addCount();
        changeTeam();
    }
}
