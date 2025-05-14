package menu_page;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import trailfood.MainApplication;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

// USE HASHMAPS, SOOO COOOLL BRUHH
public class OrderController {
  @FXML
  private Label orderIdLabel;

  @FXML
  private Label orderPriceLabel;

  @FXML
  private Label orderDateLabel;

  @FXML
  private VBox orderItemsVBox;

  @FXML
  private Label placeOrderLabel;

  @FXML
  public void setPlaceOrderLabelOnAction() {
    placeOrderLabel.setOnMouseClicked(event -> {
      try {
        FXMLLoader fxmlLoader = new FXMLLoader(
            MainApplication.class.getResource("/trailfood/menu_page/PlacePayment.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.setTitle("Place Payment");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  private final Map<String, OrderItem> orderItems = new HashMap<>();
  
  public void setData(Order order) {
    orderIdLabel.setText(String.valueOf(order.getOrderId()));
    orderPriceLabel.setText(String.valueOf(order.getOrderPrice()));
    orderDateLabel.setText(String.valueOf(order.getOrderDate()));
  }

  public void updateOrderItem(MenuItem menuItem, int quantity) {
    String name = menuItem.getName();
    OrderItem item = orderItems.get(name);
    
    if (item != null){
      item.setQuantity(quantity);
      item.setPrice(Double.parseDouble(menuItem.getPrice()) * quantity);
    } else {
      item = new OrderItem();
      item.setOrderItemName(menuItem.getName());
      item.setPrice(Double.parseDouble(menuItem.getPrice()));
      item.setQuantity(quantity);
      orderItems.put(name, item);
    }

    displayOrders();
  }

  public void deleteOrderItem(MenuItem menuItem) {
    String name = menuItem.getName();
    OrderItem item = orderItems.get(name);

    if (item != null){
      orderItems.remove(name);
      displayOrders();
    }
  }

  public void displayOrders(){
    try {
      orderItemsVBox.getChildren().clear();
      for (OrderItem item : orderItems.values()) {
        FXMLLoader fxmlLoader = new FXMLLoader(
            MainApplication.class.getResource("/trailfood/menu_page/OrderItem.fxml"));
        HBox HBox = fxmlLoader.load();

        OrderItemController orderItemController = fxmlLoader.getController();
        orderItemController.setOrderItem(item, item.getQuantity());

        orderItemsVBox.getChildren().add(HBox);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
