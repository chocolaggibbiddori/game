package mygame.game.chess.converter;

import lombok.extern.slf4j.Slf4j;
import mygame.game.chess.point.ChessPoint;
import mygame.point.Point;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToPointConverter implements Converter<String, Point> {

    @Override
    public Point convert(String source) {
        Point point = new ChessPoint(source);
        log.info("convert source={} to result={}", source, point);
        return point;
    }
}
