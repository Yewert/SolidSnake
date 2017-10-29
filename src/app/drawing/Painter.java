package app.drawing;

import app.Settings;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import model.creatures.CreatureType;
import model.creatures.CreatureTypeValidator;
import model.game.GameFrame;
import model.utils.Direction;
import model.utils.Point;

import static model.utils.Direction.*;

public class Painter {
    private Settings settings;
    private static GameFrame prevFrame = null;

    public Painter(Settings settings){
        this.settings = settings;
    }

    public void paint(GameFrame frame, GraphicsContext gc){
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0,0, settings.getWidth(), settings.getHeight());

        if (frame != null) {
            paintFrame(frame, gc);
            paintScore(frame, gc);
            prevFrame = frame;
        } else {
            if (prevFrame == null) {
                throw new IllegalArgumentException("Previous frame was null");
            }

            paintFrame(prevFrame, gc);
            paintScore(prevFrame, gc);
            paintResetMessage(gc);
        }
    }

    private void paintFrame(GameFrame frame, GraphicsContext gc){
        frame.getCreaturesInfo().forEach((p, ci) -> {
//            gc.setFill(Settings.getColorDict()
//                    .get(CreatureToTextureConverter.converters
//                            .get(ci.getType()))
//                    .apply(ci.getPlayerNumber()));
//            gc.drawImage(settings.getSkins()
//                    .getSpritesForPlayers()
//                    .get(ci.getPlayerNumber())
//                    .get(CreatureToTextureConverter.converters.get(ci.getType())));

            if(CreatureTypeValidator.isSnake(ci.getType())){
                Image image = settings
                        .getSkins()
                        .getSpritesForPlayers()
                        .get(ci.getPlayerNumber())
                        .get(CreatureToTextureConverter.converters.get(ci.getType()));
//                Image rotatedImage = rotateImage(image, ci);
//                gc.drawImage(
//                        rotatedImage,
//                        p.getX() * settings.getSize(),
//                        p.getY() * settings.getSize(),
//                        settings.getSize(),
//                        settings.getSize()
//                );
                double angle = ci.getType() == CreatureType.SnakeHead
                        ? 0
                        : getAngleFromDirection(ci.getDirection());
                drawRotatedImage(
                        gc, image, angle,
                        p.getX() * settings.getSize(),
                        p.getY() * settings.getSize(),
                        settings.getSize(),
                        settings.getSize()
                );
            }
            else{
                gc.drawImage(
                        settings.getSkins()
                                .getSpritesForSubjects()
                                .get(CreatureToTextureConverter.converters.get(ci.getType())),
                        p.getX() * settings.getSize(),
                        p.getY() * settings.getSize(),
                        settings.getSize(),
                        settings.getSize()
                );
            }
        });
    }

    private void paintPoint(Point point, GraphicsContext gc) {
        gc.fillRect(point.getX() * settings.getSize(),
                point.getY() * settings.getSize(),
                settings.getSize(),
                settings.getSize());
    }

    private void paintResetMessage(GraphicsContext gc) {
        gc.setFill(Color.AQUAMARINE);
        gc.fillText("Hit ENTER to reset.", 10, settings.getHeight() - 10);
    }

    private static void paintScore(GameFrame frame, GraphicsContext gc){
        gc.setFill(Color.BEIGE);
        for (int i = 0; i < frame.getScores().length; i++) {
            int s = i + 1;
            gc.fillText("Player " + s +": " + frame.getScores()[i], 10, 10 + i*20);
        }
    }

    private Image rotateImage(Image image, GameFrame.CreatureInfo creatureInfo){
        if (creatureInfo.getType() == CreatureType.SnakeHead 
            || creatureInfo.getDirection() == Up
            || creatureInfo.getDirection() == None)
            return image;

        ImageView iv = new ImageView(image);
        iv.setRotate(getAngleFromDirection(creatureInfo.getDirection()));
        SnapshotParameters params = new SnapshotParameters();
        return iv.snapshot(params, null);
    }

    private double getAngleFromDirection(Direction direction){
        switch (direction){
            case None:
            case Up:
                return 0;
            case Right:
                return 90;
            case Down:
                return 180;
            case Left:
                return 270;
        }
        return 0;
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    private void drawRotatedImage(
            GraphicsContext gc,
            Image image,
            double angle,
            double x,
            double y,
            double width,
            double height) {
        gc.save();
        rotate(gc, angle, x + width / 2, y + height / 2);
        gc.drawImage(image, x, y, width, height);
        gc.restore();
    }
}
