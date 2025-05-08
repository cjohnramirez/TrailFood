package practice;
import javafx.application.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    // Setting new color
    Color blueeee = Color.rgb(0, 0, 255);

    // Creating a new Image object
    String link = "https://encrypted-tbn1.gstatic.com" 
         + "/images?q=tbn:ANd9GcRQub4GvEezKMsiIf67U" 
         + "rOxSzQuQ9zl5ysnjRn87VOC8tAdgmAJjcwZ2qM";
    Image image = new Image(link);
    ImagePattern radialGradient = new ImagePattern(image, 20, 20, 50, 50, false);

    // Creating a linear gradient
    Stop[] stops = new Stop[] {
      new Stop(0, Color.RED),
      new Stop(1, Color.YELLOW)
    };
    LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

    // Creating a SVGPath object
    SVGPath svgPath = new SVGPath();
    String path = "M 70 110 C 70 180, 210 180, 210 110";
    svgPath.setContent(path);
    svgPath.setFill(Color.VIOLET);
    svgPath.setStroke(blueeee);
    svgPath.setStrokeWidth(5);

    // Creating a Circle object
    Circle circle = new Circle();
    circle.setCenterX(300.0f);
    circle.setCenterY(300.0f);
    circle.setRadius(50.0f);
    circle.setFill(linearGradient);

    // Creating a Rectangle object
    Rectangle rectangle = new Rectangle();
    rectangle.setX(0);
    rectangle.setY(100);
    rectangle.setWidth(200);
    rectangle.setHeight(100);
    rectangle.setFill(radialGradient);

    // Setting the roundness of Rectangle
    rectangle.setArcWidth(30.0);
    rectangle.setArcHeight(20.0);
    rectangle.setStroke(Color.YELLOW);

    // Subtract operation
    Shape shape = Shape.subtract(svgPath, rectangle);

    // Creating a Text object
    Text text = new Text();

    // Configuring the text object
    text.setX(100);
    text.setY(20);
    text.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
    text.setStrikethrough(true);

    // Setting the text
    text.setText("Hello JavaFX");

    // Creating a line object
    Line line = new Line();

    // Configuring the line object
    line.setStartX(100.0);
    line.setStartY(150.0);
    line.setEndX(400.0);
    line.setEndY(250.0);
    line.setStrokeWidth(20);
    line.setStroke(Color.FORESTGREEN);
    line.setStrokeLineCap(StrokeLineCap.ROUND);

    // Create a new Group object
    Group root = new Group();

    // Adding line and text to the group
    root.getChildren().addAll(line, text, rectangle, circle, svgPath, shape);

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
