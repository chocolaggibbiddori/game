package chocola.chess;

class NormalNotation implements Notation {

    private final String unitInitial;
    private final char assist;
    private final boolean capture;
    private final Tile to;
    private final String promotion;
    private final AttackType attack;

    NormalNotation(String unitInitial, char assist, boolean capture, Tile to, String promotion, AttackType attack) {
        this.unitInitial = unitInitial;
        this.assist = assist;
        this.capture = capture;
        this.to = to;
        this.promotion = promotion;
        this.attack = attack;
    }

    @Override
    public String notate() {
        StringBuilder sb = new StringBuilder();

        sb.append(unitInitial);
        if (assist != ' ') sb.append(assist);
        if (capture) sb.append("x");
        sb.append(to.toString());
        if (!promotion.isEmpty()) sb.append("=").append(promotion);
        if (attack == AttackType.CHECKMATE) sb.append("#");
        else if (attack == AttackType.CHECK) sb.append("+");

        return sb.toString();
    }
}
