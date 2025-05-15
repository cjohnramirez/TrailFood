package menu_page;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
  private String orderOption = "Takeaway";
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
    System.out.println("Payment method: " + paymentMethod);
    System.out.println("Payment amount: " + paymentAmountTextField.getText());
    System.out.println("User name: " + MainApplication.getUserId());
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
        saveOrder();
        paymentAmountStatusLabel.setText("Payment successful");
        paymentAmountStatusLabel.setStyle("-fx-text-fill: -color-success-5;");
        System.out.println("Payment successful. Amount: " + enteredAmount);
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
      System.out.println("Order item: " + orderItem.getOrderItemName() + ", Price: " + orderItem.getPrice() +
          ", Quantity: " + orderItem.getQuantity());
    }
    System.out.println("Total order price: " + order.getOrderPrice());
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
      } else {
        System.out.println("Creating order failed, no ID obtained.");
      }
      System.out.println("Order saved successfully");
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
        System.out.println("Menu item ID Saved: " + menuItemId);
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
        System.out.println("Order items saved successfully");
      } catch (Exception e) {
        e.printStackTrace();
        e.getCause();
      }
    }
  }
}
