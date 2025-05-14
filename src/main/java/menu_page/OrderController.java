package menu_page;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    printOrderItems();  
  }

  public void deleteOrderItem(MenuItem menuItem) {
    String name = menuItem.getName();
    OrderItem item = orderItems.get(name);

    if (item != null){
      orderItems.remove(name);
      displayOrders();
    }
    
    printOrderItems();
  }

  public void printOrderItems() {
    System.out.println("--------------------------");
    for (OrderItem item : orderItems.values()) {
      System.out.println("Order Item: " + item.getOrderItemName() + ", Quantity: " + item.getQuantity() + ", Price: "
          + item.getPrice());
    }
    System.out.println("--------------------------");
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
