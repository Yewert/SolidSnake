package app.drawing;

import app.Settings;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.game.GameFrame;
import model.utils.Point;

public class Painter {
    private static int size = Settings.getSize();
    private static GameFrame prevFrame = null;

    public static void paint(GameFrame frame, GraphicsContext gc){
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0,0, Settings.getWidth(), Settings.getHeight());

        if (frame != null) {
            paintFrame(frame, gc);
            paintScore(frame, gc);
            prevFrame = frame;
        }else {

            if (prevFrame == null) {
                throw new IllegalArgumentException("Previous frame was null");
            }

            paintFrame(prevFrame, gc);
            paintScore(prevFrame, gc);
            paintResetMessage(gc);
        }
    }

    private static void paintFrame(GameFrame frame, GraphicsContext gc){
        frame.getCreaturesInfo().forEach((p, ci) -> {
            gc.setFill(Settings.getColorDict().get(CreatureToTextureConverter.converters.get(ci.getType())));
            paintPoint(p, gc);
        });
    }

    private static void paintPoint(Point point, GraphicsContext gc) {
        gc.fillRect(point.getX() * size, point.getY() * size, size, size);
    }

    private static void paintResetMessage(GraphicsContext gc) {
        gc.setFill(Color.AQUAMARINE);
        gc.fillText("Hit ENTER to reset.", 10, Settings.getHeight() - 10);
    }

    private static void paintScore(GameFrame frame, GraphicsContext gc){
        gc.setFill(Color.BEIGE);
        for (int i = 0; i < frame.getScores().length; i++) {
            int s = i + 1;
            gc.fillText("Player " + s +": " + frame.getScores()[i], 10, 10 + i*20);
        }
    }
}
