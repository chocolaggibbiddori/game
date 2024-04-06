package chocola.omok;

enum Team {

    BLACK("흑") {
        @Override
        Team nextTurn() {
            return Team.WHITE;
        }

        @Override
        public String toStoneString() {
            return "1";
        }
    },

    WHITE("백") {
        @Override
        Team nextTurn() {
            return Team.BLACK;
        }

        @Override
        public String toStoneString() {
            return "2";
        }
    };

    final String teamName;

    Team(String teamName) {
        this.teamName = teamName;
    }

    abstract Team nextTurn();

    abstract String toStoneString();
}
