package history_page;

import java.io.InputStream;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menu_page.OrderItem;

// where an order item will be displayed
public class HistoryOrderItemController {
  @FXML
  private Label orderItemNameLabel;

  @FXML
  private Label orderItemPriceLabel; 

  @FXML
  private Label orderItemQuantityLabel;

  @FXML
  private ImageView orderItemImageView;

  public void setHistoryOrderItem(OrderItem orderItem) {
    orderItemNameLabel.setText(orderItem.getOrderItemName());
    orderItemPriceLabel.setText(Double.toString(orderItem.getPrice()));
    orderItemQuantityLabel.setText("QTY: " + Integer.toString(orderItem.getQuantity()));
    InputStream is = getClass().getResourceAsStream(orderItem.getImagePath());
        if (is == null) {
            System.err.println("Image not found at: " + orderItem.getImagePath());
        } else {
            orderItemImageView.setImage(new Image(is));
            orderItemImageView.setFitHeight(100);
            orderItemImageView.setFitWidth(150);
            orderItemImageView.setPreserveRatio(false);
        }
  }
}
