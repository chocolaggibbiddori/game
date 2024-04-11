package chocola.omok;

import java.util.function.Function;

class OmokConverter implements Function<String, OmokPoint> {

    private static final OmokConverter INSTANCE = new OmokConverter();
    private final OmokPoint[][] OMOK_POINTS;

    private OmokConverter() {
        OMOK_POINTS = new OmokPoint[16][16];
        for (int i = 1; i < OMOK_POINTS.length; i++)
            for (int j = 1; j < OMOK_POINTS[i].length; j++)
                OMOK_POINTS[i][j] = new OmokPoint(i, j);
    }

    static OmokConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public OmokPoint apply(String input) {
        String[] split = input.split(",");
        String s1 = split[0].trim();
        String s2 = split[1].trim();
        int x = Integer.parseInt(s1);
        int y = Integer.parseInt(s2);
        return OMOK_POINTS[x][y];
    }
}
