package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public abstract class MoveCheckedPiece extends Piece {

    private boolean moved;

    public MoveCheckedPiece(Team team) {
        super(team);
        this.moved = false;
    }

    public final boolean isMoved() {
        return moved;
    }

    public void moveTo(Tile to) {
        super.moveTo(to);
        moved = true;
    }
}
