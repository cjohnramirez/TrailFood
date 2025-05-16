package history_page;

import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import menu_page.Order;
import menu_page.OrderItem;

// where order items of a specific order will be displayed
public class HistoryOrderController {
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

  private List<OrderItem> orderItems;
  private Order order;

  @FXML
  private void historyOrderVBoxOnClick(MouseEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trailfood/history_page/HistoryOrderWindow.fxml"));
      Parent root = fxmlLoader.load();

      HistoryOrderWindowController orderWindowController = fxmlLoader.getController();
      orderWindowController.setHistoryOrder(order, orderItems);

      Stage newStage = new Stage();
      newStage.setTitle("Place Payment");
      newStage.setScene(new Scene(root));
      newStage.show();

      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
      newStage.setX((screenBounds.getWidth() - newStage.getWidth()) / 2);
      newStage.setY((screenBounds.getHeight() - newStage.getHeight()) / 2);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setHistoryOrder(Order order, List<OrderItem> orderItems) {
    this.order = order;
    this.orderItems = orderItems;

    historyOrderIDLabel.setText(order.getOrderId());
    historyOrderTypeLabel.setText(order.getOrderOption());
    historyOrderPaymentLabel.setText(order.getOrderPaymentMethod());
    historyOrderDateLabel.setText(order.getOrderDate());
    historyOrderPriceLabel.setText(String.valueOf(order.getOrderPrice()));
  }
}
