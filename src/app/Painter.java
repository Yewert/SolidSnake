package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import models.Point;

class Painter {
    private static int size = Settings.getSize();
    private static GameFrame _prevFrame = null;

    static void paint(GameFrame frame, GraphicsContext gc){
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0,0, Settings.getWidth(), Settings.getHeight());

        if (frame != null) {
            paintFrame(frame, gc);
            paintScore(frame, gc);
            _prevFrame = frame;
            //TODO: draw dead Snake
        }

        if (_prevFrame == null){
            throw new IllegalArgumentException("Previous frame was null");
        }

        paintFrame(_prevFrame, gc);
        paintScore(frame, gc);
        paintResetMessage(gc);
        _prevFrame = null;
    }

    private static void paintFrame(GameFrame frame, GraphicsContext gc){
        frame.getTexturesInfo().forEach((p, ti) -> {
            gc.setFill(Settings.getColorDict().get(ti.getType()));
            //considering that Point is (col, row), not pixels
            paintPoint(p, gc);
        });
    }

    private static void paintPoint(Point point, GraphicsContext gc) {
        gc.fillRect(point.getX() * size, point.getY() * size, size, size);
    }

    private static void paintResetMessage(GraphicsContext gc) {
        gc.setFill(Color.AQUAMARINE);
        gc.fillText("Hit RETURN to reset.", 10, Settings.getHeight() - 10);
    }

    private static void paintScore(GameFrame frame, GraphicsContext gc){
        gc.setFill(Color.BEIGE);
        for (int i = 0; i < frame.getScores().length; i++) {
            gc.fillText("Player " + i + 1 +": " + frame.getScores()[i], 10, 10 + i*20);
        }
    }
}
