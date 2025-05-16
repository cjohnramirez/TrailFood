package menu_page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import trailfood.MainApplication;
import java.util.List;

public class PaymentSuccessfulController {
  @FXML
  private Label totalPaymentAmountLabel;

  @FXML
  private Label totalOrderCostLabel;

  @FXML
  private Label totalChangeLabel;

  @FXML
  private ScrollPane orderItemsScrollPane;

  @FXML
  private VBox orderItemsVBox;

  @FXML
  private Label orderConfirmLabel;

  private double totalPaymentAmount;
  private double totalOrderCost;
  private double totalChange;

  @FXML
  public void setOrderConfirmOnAction(MouseEvent event) {
    Stage stage = (Stage) orderConfirmLabel.getScene().getWindow();
    stage.close();
  }

  public void setTotalPaymentAmountLabel(double totalPaymentAmount) {
    this.totalPaymentAmount = totalPaymentAmount;
    totalPaymentAmountLabel.setText(String.format("PHP %.2f", totalPaymentAmount));
  }

  public void setTotalOrderCostLabel(double totalOrderCost) {
    this.totalOrderCost = totalOrderCost;
    totalOrderCostLabel.setText(String.format("PHP %.2f", totalOrderCost));
  }

  public void setTotalChangeLabel() {
    this.totalChange = totalPaymentAmount - totalOrderCost;
    totalChangeLabel.setText(String.format("PHP %.2f", totalChange));
  }

  public void setOrderItemsScrollPane(List<OrderItem> orderItems) {
    try {
      setTotalChangeLabel();

      for (OrderItem item : orderItems) {
        FXMLLoader fxmlLoader = new FXMLLoader(
            MainApplication.class.getResource("/trailfood/menu_page/OrderItem.fxml"));
        HBox hBox = fxmlLoader.load();

        OrderItemController orderItemController = fxmlLoader.getController();
        orderItemController.setOrderItem(item, item.getQuantity());

        orderItemsVBox.getChildren().add(hBox);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
