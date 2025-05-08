package practice;
import javafx.application.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    // Creating a SVGPath object
    SVGPath svgPath = new SVGPath();
    String path = "M 70 110 C 70 180, 210 180, 210 110";
    svgPath.setContent(path);
    svgPath.setFill(Color.VIOLET);
    svgPath.setStroke(Color.RED);

    // Creating a Circle object
    Circle circle = new Circle();
    circle.setCenterX(300.0f);
    circle.setCenterY(300.0f);
    circle.setRadius(50.0f);

    // Creating a Rectangle object
    Rectangle rectangle = new Rectangle();
    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(200);
    rectangle.setHeight(100);

    // Setting the roundness of Rectangle
    rectangle.setArcWidth(30.0);
    rectangle.setArcHeight(20.0);

    // Creating a Text object
    Text text = new Text();

    // Configuring the text object
    text.setFont(new Font(45));
    text.setX(50);
    text.setY(150);

    // Setting the text
    text.setText("Hello JavaFX");

    // Creating a line object
    Line line = new Line();

    // Configuring the line object
    line.setStartX(100.0);
    line.setStartY(150.0);
    line.setEndX(300.0);
    line.setEndY(150.0);

    // Create a new Group object
    Group root = new Group();

    // Adding line and text to the group
    root.getChildren().addAll(line, text, rectangle, circle, svgPath);

    // Creating a Secen by passing the group object, height and width
    Scene scene = new Scene(root, 600, 300);

    // Setting color to Scene
    scene.setFill(Color.BROWN);

    // Setting title to Stage
    primaryStage.setTitle("JavaFX Application");

    // Adding Scene to Stage
    primaryStage.setScene(scene);

    // Displaying contents of Stage
    primaryStage.show();
  }
  public static void main(String[] args) {
    launch(args);
  }
}
