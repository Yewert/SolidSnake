package app;


import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import app.menus.menu.Menu;
import app.menus.menu.MenuButton;
import app.menus.mainMenu.MainMenu;
import app.menus.pauseMenu.PauseMenu;
import app.drawing.Painter;
import model.utils.Direction;
import model.game.Game;
import model.game.GameFrame;

import java.util.Map;

public class App extends Application {

    //TODO: how to get rid of static?
    private static Game game;
    private static GameFrame frame;
    private static GraphicsContext context;
    private static boolean isGameOver;
    private static boolean isPaused = false;
    private static Direction[] currDir;
    private static int snakeCount = 1;
    private static Stage theStage;
    private static AnimationTimer gameLoop;
    private static Settings settings;
    private static Painter painter;
    private static int width = 800;
    private static int height = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake Reborn");
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        primaryStage.setOnCloseRequest(e -> System.exit(0));

        settings = new Settings(20,
                new SkinSettings(1, 1 ,1),
                new GameplaySettings(GameplaySettings.getRandomField(30, 30, snakeCount),
                        true,
                        20,
                        50,
                        40,
                        30,
                        snakeCount)
                );
        painter = new Painter(settings);
        theStage = primaryStage;
        theStage.setScene(new Scene(createMainMenu(), Color.BLACK));
        theStage.show();
    }

     private void playSnake(int snakeCount){
        App.snakeCount = snakeCount;
        reset(App.snakeCount);
        theStage.setScene(new Scene(createGamePlay(), Color.BLACK));
        gameLoop.start();
    }

    private Parent createGamePlay(){
        StackPane root = new StackPane();
        root.setPrefSize(width, height);

        Menu pauseMenu = new PauseMenu();
        Map<String, MenuButton> bm = pauseMenu.getButtonsMap();
        bm.get("pauseResume").setOnMouseClicked(event -> {
            isPaused = false;
            root.getChildren().remove(pauseMenu);
            pauseMenu.reload();
        });
        bm.get("pauseRestart").setOnMouseClicked(event -> {
            isPaused = false;
            root.getChildren().remove(pauseMenu);
            pauseMenu.reload();
            reset(snakeCount);
        });
        bm.get("quitYes").setOnMouseClicked(event -> {
            isPaused = false;
            gameLoop.stop();
            FadeTransition fade = new FadeTransition(Duration.millis(300), root);
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> {
                theStage.setScene(new Scene(createMainMenu(), Color.BLACK));
            });
            fade.play();
        });

        Canvas canvas = new Canvas(width, height);
        canvas.setFocusTraversable(true);
        root.getChildren().add(canvas);

        context = canvas.getGraphicsContext2D();

        canvas.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    currDir[0] = Direction.Up;
                    break;
                case S:
                    currDir[0] = Direction.Down;
                    break;
                case A:
                    currDir[0] = Direction.Left;
                    break;
                case D:
                    currDir[0] = Direction.Right;
                    break;
                case UP:
                    currDir[1] = Direction.Up;
                    break;
                case DOWN:
                    currDir[1] = Direction.Down;
                    break;
                case LEFT:
                    currDir[1] = Direction.Left;
                    break;
                case RIGHT:
                    currDir[1] = Direction.Right;
                    break;
                case NUMPAD8:
                    currDir[2] = Direction.Up;
                    break;
                case NUMPAD5:
                    currDir[2] = Direction.Down;
                    break;
                case NUMPAD4:
                    currDir[2] = Direction.Left;
                    break;
                case NUMPAD6:
                    currDir[2] = Direction.Right;
                    break;
                case ENTER:
                    if (isGameOver) {
                        reset(snakeCount);
                    }
                    break;
                case ESCAPE:
                    if (isPaused) {
                        isPaused = false;
                        root.getChildren().remove(pauseMenu);
                        pauseMenu.reload();
                    } else {
                        isPaused = true;
                        root.getChildren().add(pauseMenu);
                    }
            }
        });

        gameLoop = new AnimationTimer(){

            private long prevTime = 0;

            @Override
            public void handle(long now) {
                if (!isGameOver && !isPaused) {
                    if ((now - prevTime) >= 100 * 1000000) {
                        prevTime = now;
                        Direction[] directions = new Direction[snakeCount];
                        System.arraycopy(currDir, 0, directions, 0, snakeCount);
                        frame = game.makeTurn(directions);
                        if (frame == null) {
                            isGameOver = true;
                        }
                    }
                }
              painter.paint(frame, context);
            }
        };

        return root;
    }

    private Direction[] extractDirectionsCorrepondingToSnakeCount(){
        Direction[] result = new Direction[snakeCount];
        System.arraycopy(currDir, 0, result, 0, snakeCount);
        return result;
    }

    private Parent createMainMenu(){
        StackPane root = new StackPane();
        root.setPrefSize(width, height);
        root.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.BLACK,
                                CornerRadii.EMPTY,
                                Insets.EMPTY)
                )
        );

        ImageView snakeLogo = new ImageView(
                new Image("images/snakeLogoHD.png",
                        600,
                        0,
                        true,
                        true)
        );
        FadeTransition logoFade = new FadeTransition(Duration.millis(1000), snakeLogo);
        logoFade.setFromValue(0);
        logoFade.setToValue(1);

        snakeLogo.setOpacity(0);
        root.getChildren().add(snakeLogo);
        StackPane.setAlignment(snakeLogo, Pos.TOP_CENTER);

        Menu mainMenu = new MainMenu();
        Map<String, MenuButton> mb = mainMenu.getButtonsMap();
        mb.get("playSolo").setOnMouseClicked(event -> {
            FadeTransition fade = new FadeTransition(Duration.millis(200), root);
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> playSnake(1));
            fade.play();
        });
        mb.get("playDuo").setOnMouseClicked(event -> {
            FadeTransition fade = new FadeTransition(Duration.millis(200), root);
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> playSnake(2));
            fade.play();
        });
        mb.get("playTrio").setOnMouseClicked(event -> {
            FadeTransition fade = new FadeTransition(Duration.millis(200), root);
            fade.setFromValue(1);
            fade.setToValue(0);
            fade.setOnFinished(e -> playSnake(3));
            fade.play();
        });

        FadeTransition startFade = new FadeTransition(Duration.millis(1000), mainMenu);
        startFade.setFromValue(0);
        startFade.setToValue(1);
        logoFade.setOnFinished(event -> {
            startFade.play();
            mainMenu.setOpacity(0);
            root.getChildren().add(mainMenu);
        });
        logoFade.play();

        return root;
    }

    private static void reset(int snakeCount) {
        isGameOver = false;
        currDir = new Direction[3];
        for (int i = 0; i < currDir.length; i++) {
            currDir[i] = Direction.None;
        }
        settings.setGameplaySettings(new GameplaySettings(GameplaySettings.getRandomField(30, 30, snakeCount),
                true,
                20,
                50,
                40,
                30,
                snakeCount)
        );
        game = new Game(settings.getGameplaySettings());
        Direction[] directions = new Direction[snakeCount];
        System.arraycopy(currDir, 0, directions, 0, snakeCount);
        frame = game.makeTurn(directions);
        //TODO: refresh skins?
    }
}
