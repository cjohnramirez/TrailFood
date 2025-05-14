package menu_page;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class PlacePaymentController {
  @FXML
  private Label placePaymentCancel;

  @FXML
  public void setPlacePaymentCancelOnAction() {
    placePaymentCancel.setOnMouseClicked(event -> {
      Stage stage = (Stage) placePaymentCancel.getScene().getWindow();
      stage.close();
    });
  }
}
