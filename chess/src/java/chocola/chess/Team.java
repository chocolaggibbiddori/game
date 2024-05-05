package chocola.chess;

public enum Team {

    WHITE {
        @Override
        public String toString() {
            return "백";
        }
    },
    BLACK {
        @Override
        public String toString() {
            return "흑";
        }
    };

    public static Team getEnemyTeam(Team team) {
        return team == WHITE ? BLACK : WHITE;
    }
}
