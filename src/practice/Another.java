package practice;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Another extends Application {
  @Override 
  public void start(Stage primaryStage) throws Exception {
    Group group = new Group();
    Scene scene = new Scene(group, 600, 600);

    primaryStage.setTitle("JavaFX");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
