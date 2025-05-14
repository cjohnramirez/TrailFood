package menu_page;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

public class OrderItemController {
  @FXML
  private HBox orderItems;

  @FXML
  private Label orderItemNameLabel;

  @FXML
  private Label orderQuantityLabel;

  @FXML
  private Label orderPriceLabel;

  public void setOrderItem(OrderItem orderItem, int quantity) {
    orderItemNameLabel.setText(orderItem.getOrderItemName());
    orderQuantityLabel.setText(String.valueOf(quantity));
    orderPriceLabel.setText("PHP " + String.valueOf(orderItem.getPrice()));
  }
}
