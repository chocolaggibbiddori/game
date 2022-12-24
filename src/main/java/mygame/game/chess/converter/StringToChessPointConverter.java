package mygame.game.chess.converter;

import lombok.extern.slf4j.Slf4j;
import mygame.game.chess.point.ChessPoint;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToChessPointConverter implements Converter<String, ChessPoint> {

    @Override
    public ChessPoint convert(String source) {
        ChessPoint point = new ChessPoint(source);
        log.info("convert source={} to result={}", source, point);
        return point;
    }
}
