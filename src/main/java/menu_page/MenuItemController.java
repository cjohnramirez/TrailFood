package menu_page;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.InputStream;

import javafx.fxml.FXML;

public class MenuItemController {
  @FXML
  private Label itemNameLabel;

  @FXML
  private Label itemPriceLabel;

  @FXML
  private ImageView itemImage;

  @FXML
  private Label itemQuantityIncrementLabel;
  
  @FXML
  private Label itemQuantityDecrementLabel;

  @FXML
  private Label itemQuantityLabel;

  private MenuItem menuItem;
  private MenuItemListener menuItemListener;

  @FXML
  public void incrementQuantity(MouseEvent event) {
    menuItemListener.incrementQuantityListener(menuItem);
  }

  @FXML
  public void decrementQuantity(MouseEvent event) {
    menuItemListener.decrementQuantityListener(menuItem);
  }

  public void setData(MenuItem menuItem, MenuItemListener menuItemListener) {
    this.menuItem = menuItem;
    this.menuItemListener = menuItemListener;
    itemNameLabel.setText(menuItem.getName());
    itemPriceLabel.setText("PHP " + menuItem.getPrice());

    // fail-safe for any files reading, use this
    InputStream is = getClass().getResourceAsStream(menuItem.getImage());
    if (is == null) {
      System.err.println("Image not found at: " + menuItem.getImage());
    } else {
      itemImage.setImage(new Image(is));
      itemImage.setFitHeight(100);
      itemImage.setFitWidth(150);
      itemImage.setPreserveRatio(false);
    }
  }

  public void setItemQuantityLabel(int quantity) {
    itemQuantityLabel.setText("Qty: " + String.valueOf(quantity));
  }
}
