package mygame.turn;

public interface Turn {

    void turnStart();

    void addCount();

    int getCount();

    String getCurrentTeam();

    String getNotation();

    void changeTeam();
}
