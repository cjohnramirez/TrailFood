package history_page;

import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import menu_page.Order;
import menu_page.OrderItem;

// where order items of a specific order will be displayed
public class HistoryOrderWindowController {
  @FXML
  private Label historyOrderIDLabel;

  @FXML
  private Label historyOrderTypeLabel;

  @FXML
  private Label historyOrderPaymentLabel;

  @FXML
  private Label historyOrderDateLabel;

  @FXML
  private Label historyOrderPriceLabel;

  @FXML
  private VBox historyOrderItemsVBox;

  public void setHistoryOrder(Order order, List<OrderItem> orderItems) {
    historyOrderIDLabel.setText(order.getOrderId());
    historyOrderTypeLabel.setText(order.getOrderOption());
    historyOrderPaymentLabel.setText(order.getOrderPaymentMethod());
    historyOrderDateLabel.setText(order.getOrderDate());
    historyOrderPriceLabel.setText(String.valueOf(order.getOrderPrice()));

    for (OrderItem orderItem : orderItems) {
      try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trailfood/history_page/HistoryOrderItem.fxml"));
        HBox orderItemBox = fxmlLoader.load();
        historyOrderItemsVBox.getChildren().add(orderItemBox);
        orderItemBox.maxWidth(Double.MAX_VALUE);

        HistoryOrderItemController orderItemController = fxmlLoader.getController();
        orderItemController.setHistoryOrderItem(orderItem);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
