package menu_page;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import trailfood.DatabaseConnector;
import trailfood.MainApplication;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;

public class PlacePaymentController {
  @FXML
  private Label placePaymentLabel;

  @FXML
  private Label placePaymentCancelLabel;

  @FXML
  private Label cashPaymentMethodLabel;

  @FXML
  private Label cardPaymentMethodLabel;

  @FXML
  private Label digitalPaymentMethodLabel;

  @FXML
  private TextField paymentAmountTextField;

  @FXML
  private Label paymentAmountStatusLabel;

  private String paymentMethod = "Cash";
  private String orderOption = "Dine In";
  private Order order = new Order();
  private final List<OrderItem> orderItems = new ArrayList<>();
  public int newRow = 0;

  @FXML
  public void setPlacePaymentCancelLabelOnAction(MouseEvent event) {
    Stage stage = (Stage) placePaymentCancelLabel.getScene().getWindow();
    stage.close();
  };

  @FXML
  private void setPaymentMethodLabelOnAction(MouseEvent event) {
    cashPaymentMethodLabel.setStyle("-fx-background-color: #e8e8e8;");
    cardPaymentMethodLabel.setStyle("-fx-background-color: #e8e8e8;");
    digitalPaymentMethodLabel.setStyle("-fx-background-color: #e8e8e8;");

    ((Label) event.getSource()).setStyle("-fx-background-color: -color-success-2;");
    paymentMethod = ((Label) event.getSource()).getText();
  }

  @FXML
  private void setPlacePaymentOnAction(MouseEvent event) {
    if (paymentAmountTextField.getText().isEmpty()) {
      paymentAmountStatusLabel.setText("Please enter payment amount");
      paymentAmountStatusLabel.setStyle("-fx-text-fill: -color-danger-5;");
      return;
    }

    try {
      double enteredAmount = Double.parseDouble(paymentAmountTextField.getText());
      setOrder();

      if (enteredAmount < order.getOrderPrice()) {
        paymentAmountStatusLabel.setText("Insufficient payment amount");
        paymentAmountStatusLabel.setStyle("-fx-text-fill: -color-danger-5;");
      } else {
        paymentAmountStatusLabel.setText("Payment successful");
        paymentAmountStatusLabel.setStyle("-fx-text-fill: -color-success-5;");
        saveOrder();
        displayOrders();
      }
    } catch (NumberFormatException e) {
      paymentAmountStatusLabel.setText("Please enter a valid number.");
      paymentAmountStatusLabel.setStyle("-fx-text-fill: -color-danger-5;");
    } catch (Exception e) {
      paymentAmountStatusLabel.setText("An error occurred during payment.");
      paymentAmountStatusLabel.setStyle("-fx-text-fill: -color-danger-5;");
      e.printStackTrace();
    }
  }

  public void setOrder() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String orderDate = LocalDateTime.now().format(formatter);

    order.setOrderOption(orderOption);
    order.setOrderDate(orderDate);
    order.setOrderPaymentAmount(Integer.parseInt(paymentAmountTextField.getText()));
    order.setOrderPaymentMethod(paymentMethod);
    order.setOrderPrice(0);

    for (OrderItem orderItem : orderItems) {
      order.setOrderPrice(order.getOrderPrice() + (orderItem.getPrice() * orderItem.getQuantity()));
    }
  }

  public void setOrderOptions(String orderOption) {
    this.orderOption = orderOption;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems.clear();
    this.orderItems.addAll(orderItems);
  }

  public void saveOrder() {
    DatabaseConnector databaseConnection = new DatabaseConnector();
    Connection connection = databaseConnection.getConnection();
    String insertOrderQuery = String.format(
        "INSERT INTO `order` (user_account_id, order_price, order_created_on, order_option, order_payment_method, order_payment_amount) "
            +
            "VALUES ('%s', %f, '%s', '%s', '%s', %f);",
        MainApplication.getUserId(),
        order.getOrderPrice(),
        order.getOrderDate(),
        order.getOrderOption(),
        order.getOrderPaymentMethod(),
        order.getOrderPaymentAmount());

    try {
      PreparedStatement statement = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
      statement.executeUpdate();

      ResultSet generatedKeys = statement.getGeneratedKeys();
      if (generatedKeys.next()) {
        newRow = generatedKeys.getInt(1);
      }
      saveOrderItems();
    } catch (Exception e) {
      e.printStackTrace();
      e.getCause();
    }
  }

  public void saveOrderItems() {
    DatabaseConnector databaseConnection = new DatabaseConnector();
    Connection connection = databaseConnection.getConnection();
    for (OrderItem orderItem : orderItems) {
      int menuItemId = 0;

      try {
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT * FROM menu_item WHERE item_name = '" + orderItem.getOrderItemName() + "';");

        while (statement.getResultSet().next()) {
          menuItemId = statement.getResultSet().getInt("menu_item_id");
        }
      } catch (Exception e) {
        e.printStackTrace();
        e.getCause();
      }

      String insertOrderItemQuery = String.format(
          "INSERT INTO order_item (order_id, menu_item_id, quantity) VALUES (%d, %d, %d);",
          newRow,
          menuItemId,
          orderItem.getQuantity());

      try {
        Statement statement = connection.createStatement();
        statement.executeUpdate(insertOrderItemQuery);
      } catch (Exception e) {
        e.printStackTrace();
        e.getCause();
      }
    }
  }

  public void displayOrders() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(
          MainApplication.class.getResource("/trailfood/menu_page/PaymentSuccessful.fxml"));
      Parent root = fxmlLoader.load();

      PaymentSuccessfulController paymentSuccessfulController = fxmlLoader.getController();
      paymentSuccessfulController.setTotalPaymentAmountLabel(order.getOrderPaymentAmount());
      paymentSuccessfulController.setTotalOrderCostLabel(order.getOrderPrice());
      paymentSuccessfulController.setOrderItemsScrollPane(orderItems);

      Stage stage = (Stage) placePaymentLabel.getScene().getWindow();
      stage.close();

      Stage newStage = new Stage();
      newStage.setTitle("Place Payment");
      newStage.setScene(new Scene(root));
      newStage.initStyle(StageStyle.UNDECORATED);
      newStage.showAndWait();

      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
      newStage.setX((screenBounds.getWidth() - newStage.getWidth()) / 2);
      newStage.setY((screenBounds.getHeight() - newStage.getHeight()) / 2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
