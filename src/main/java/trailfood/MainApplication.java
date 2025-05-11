package trailfood;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        MainApplication.primaryStage = primaryStage;
        FXMLLoader primaryFXML = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Scene primaryScene = new Scene(primaryFXML.load(), 1080, 600);

        primaryStage.initStyle(
                StageStyle.UNDECORATED
        );
        primaryStage.setTitle("TrailFood");
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(fxml)));
        primaryStage.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch();
    }
}