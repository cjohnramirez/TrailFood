package trailfood;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MainPageController implements Initializable {
  @FXML
  private Pane mainPane;

  @FXML
  private HBox menuPage;

  @FXML
  private HBox historyPage;

  @FXML
  private HBox inventoryPage;

  @FXML
  private HBox dashboardPage;

  @FXML 
  private HBox logoutButton;

  @FXML
  public void menuPageOnAction(MouseEvent event) {
    mainPane.getChildren().clear();
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/trailfood/menu_page/MenuPage.fxml"));
      Pane pane = fxmlLoader.load();
      mainPane.getChildren().add(pane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void historyPageOnAction(MouseEvent event) {
    mainPane.getChildren().clear();
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/trailfood/history_page/HistoryPage.fxml"));
      Pane pane = fxmlLoader.load();
      mainPane.getChildren().add(pane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void inventoryPageOnAction(MouseEvent event) {
    mainPane.getChildren().clear();
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/trailfood/inventory_page/InventoryPage.fxml"));
      Pane pane = fxmlLoader.load();
      mainPane.getChildren().add(pane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @FXML
  public void dashboardPageOnAction(MouseEvent event) {
    mainPane.getChildren().clear();
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/trailfood/dashboard_page/DashboardPage.fxml"));
      Pane pane = fxmlLoader.load();
      mainPane.getChildren().add(pane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void logoutButtonOnAction(MouseEvent event) {
    mainPane.getChildren().clear();
    try {
      MainApplication.changeScene("/trailfood/Login.fxml");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {  
      try {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/trailfood/menu_page/MenuPage.fxml"));
        Pane pane = fxmlLoader.load();

        mainPane.getChildren().add(pane);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
}
