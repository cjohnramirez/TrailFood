package menu_page;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

import javafx.fxml.FXML;

public class MenuItemController {
  @FXML
  private Label itemNameLabel;

  @FXML
  private Label itemPriceLabel;

  @FXML
  private ImageView itemImage;

  public void setData(MenuItem menuItem) {
    itemNameLabel.setText(menuItem.getName());
    itemPriceLabel.setText(menuItem.getPrice());

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
}
