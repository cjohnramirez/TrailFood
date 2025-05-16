package history_page;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import menu_page.Order;
import menu_page.OrderItem;
import trailfood.DatabaseConnector;

public class HistoryController implements Initializable {
  @FXML
  private VBox historyVBox;

  private List<Order> orders = new ArrayList<>();
  private Map<String, List<OrderItem>> orderItemsMap = new HashMap<>();

  public void getOrderData() {
    DatabaseConnector connectNow = new DatabaseConnector();
    Connection connectDB = connectNow.getConnection();

    String fetchOrders = "SELECT * FROM `order`;";
    String fetchOrderItems = "SELECT * FROM order_item;";

    try (
        Connection connection = connectDB;
        PreparedStatement fetchOrdersStmt = connection.prepareStatement(fetchOrders);
        PreparedStatement fetchOrderItemsStmt = connection.prepareStatement(fetchOrderItems);
        ResultSet queryResult1 = fetchOrdersStmt.executeQuery();
        ResultSet queryResult2 = fetchOrderItemsStmt.executeQuery()) {

      // Fetch orders
      while (queryResult1.next()) {
        Order order = new Order();
        order.setOrderId(queryResult1.getString("order_id"));
        order.setOrderOption(queryResult1.getString("order_option"));
        order.setOrderPaymentMethod(queryResult1.getString("order_payment_method"));
        order.setOrderPaymentAmount(queryResult1.getDouble("order_payment_amount"));
        order.setOrderDate(queryResult1.getString("order_created_on"));
        order.setOrderPrice(queryResult1.getDouble("order_price"));
        orders.add(order);
      }

      // Fetch order items and group them by order_id
      while (queryResult2.next()) {
        OrderItem orderItem = new OrderItem();

        String fetchMenuItem = "SELECT * FROM menu_item WHERE menu_item_id = ?;";
        try (PreparedStatement fetchMenuItemStmt = connection.prepareStatement(fetchMenuItem)) {
          fetchMenuItemStmt.setInt(1, queryResult2.getInt("menu_item_id"));
          try (ResultSet queryResult3 = fetchMenuItemStmt.executeQuery()) {
            if (queryResult3.next()) {
              orderItem.setOrderItemName(queryResult3.getString("item_name"));
              orderItem.setPrice(queryResult3.getDouble("item_price"));
              orderItem.setQuantity(queryResult2.getInt("quantity"));
              orderItem.setImagePath(queryResult3.getString("item_image"));

              String orderId = queryResult2.getString("order_id");
              orderItemsMap.computeIfAbsent(orderId, k -> new ArrayList<>()).add(orderItem);
            }
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setHistoryVBox() {
    try {
      System.out.println("Setting history VBox");
      for (Order order : orders) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trailfood/history_page/HistoryOrder.fxml"));
        VBox orderBox = fxmlLoader.load();
        orderBox.setMaxWidth(Double.MAX_VALUE); // Ensure the VBox occupies the whole width
        historyVBox.getChildren().add(orderBox);

        HistoryOrderController orderController = fxmlLoader.getController();
        List<OrderItem> orderItemsForOrder = orderItemsMap.getOrDefault(order.getOrderId(), new ArrayList<>());
        orderController.setHistoryOrder(order, orderItemsForOrder);

        // Add gap between elements in historyVBox
        historyVBox.setSpacing(10); // Set spacing to 10 (or any desired value)
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resources) {
    getOrderData();
    setHistoryVBox();
  }
}
