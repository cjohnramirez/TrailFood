package menu_page;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import trailfood.MainApplication;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;

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
  private Label dineInLabel;

  @FXML
  private Label takeOutLabel;

  @FXML
  private Label totalOrderPriceLabel;

  @FXML
  private Label placeOrderLabel;

  @FXML
  private Label placeOrderStatusLabel;

  private final List<OrderItem> orderItems = new ArrayList<>();
  private String orderOption = "Dine In";
  private double orderPrice = 0.0;

  @FXML
  private void setOrderOptionInAction(MouseEvent event) {
    dineInLabel.setStyle("-fx-background-color: white;");
    takeOutLabel.setStyle("-fx-background-color: white;");

    ((Label) event.getSource()).setStyle("-fx-background-color: -color-success-2;");
    orderOption = ((Label) event.getSource()).getText();
  }

  @FXML
  private void setPlaceOrderLabelOnAction(MouseEvent event) {
    if (orderItems.isEmpty()) {
      placeOrderStatusLabel.setText("No items in the order.");
      return;
    }

    try {
      placeOrderStatusLabel.setText("");

      FXMLLoader fxmlLoader = new FXMLLoader(
          MainApplication.class.getResource("/trailfood/menu_page/PlacePayment.fxml"));
      Parent root = fxmlLoader.load();

      PlacePaymentController placePaymentController = fxmlLoader.getController();
      placePaymentController.setOrderItems(orderItems);
      placePaymentController.setOrderOptions(orderOption);

      Stage stage = new Stage();
      stage.setTitle("Place Payment");
      stage.setScene(new Scene(root));
      stage.initStyle(StageStyle.UNDECORATED);
      stage.show();

      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
      stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
      stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updateOrderItem(MenuItem menuItem, int quantity) {
    OrderItem existingItem = null;

    for (OrderItem item : orderItems) {
      if (item.getOrderItemName().equals(menuItem.getName())) {
        existingItem = item;
        break;
      }
    }

    if (existingItem != null) {
      existingItem.setQuantity(quantity);
      existingItem.setPrice(Double.parseDouble(menuItem.getPrice()));
    } else {
      OrderItem newItem = new OrderItem();
      newItem.setOrderItemName(menuItem.getName());
      newItem.setPrice(Double.parseDouble(menuItem.getPrice()));
      newItem.setQuantity(quantity);
      orderItems.add(newItem);
    }

    displayOrders();
  }

  public void deleteOrderItem(MenuItem menuItem) {
    orderItems.removeIf(item -> item.getOrderItemName().equals(menuItem.getName()));
    displayOrders();
  }

  public void displayOrders() {
    try {
      orderItemsVBox.getChildren().clear();
      orderPrice = 0.0;
      for (OrderItem item : orderItems) {
        FXMLLoader fxmlLoader = new FXMLLoader(
            MainApplication.class.getResource("/trailfood/menu_page/OrderItem.fxml"));
        HBox HBox = fxmlLoader.load();

        OrderItemController orderItemController = fxmlLoader.getController();
        orderItemController.setOrderItem(item, item.getQuantity());

        orderItemsVBox.getChildren().add(HBox);
        orderPrice += item.getPrice() * item.getQuantity();
      }
      totalOrderPriceLabel.setText("PHP " + orderPrice);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
