package mygame.turn;

public interface Turn {

    void turnStart();

    void addCount();

    String getCurrentTeam();

    String getNotation();

    void changeTeam();
}
