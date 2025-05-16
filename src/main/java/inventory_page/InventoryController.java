package inventory_page;

import java.sql.Statement;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import trailfood.DatabaseConnector;

public class InventoryController implements Initializable {
  @FXML
  private TableView<Inventory> inventoryTableView;

  @FXML
  private TableColumn<Inventory, Integer> itemIdTableColumn;

  @FXML
  private TableColumn<Inventory, String> itemNameTableColumn;

  @FXML
  private TableColumn<Inventory, String> itemDescriptionTableColumn;

  @FXML
  private TableColumn<Inventory, Double> itemPriceTableColumn;

  @FXML
  private TableColumn<Inventory, String> itemImageTableColumn;

  @FXML
  private TableColumn<Inventory, String> itemCategoryTableColumn;

  @FXML
  private TextField searchTextField;

  ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    DatabaseConnector connectNow = new DatabaseConnector();
    Connection connectDB = connectNow.getConnection();

    String fetchInventory = "SELECT * FROM menu_item;";

    try {
      Statement statement = connectDB.createStatement();
      ResultSet queryResult = statement.executeQuery(fetchInventory);

      while (queryResult.next()) {
        Inventory inventory = new Inventory();
        inventory.setId(queryResult.getInt("menu_item_id"));
        inventory.setItemName(queryResult.getString("item_name"));
        inventory.setItemDescription(queryResult.getString("item_description"));
        inventory.setItemPrice(queryResult.getDouble("item_price"));
        inventory.setItemImage(queryResult.getString("item_image"));

        try {
          Statement statement2 = connectDB.createStatement();
          ResultSet queryResult2 = statement2.executeQuery(
              "SELECT category_name FROM category WHERE category_id = " + queryResult.getInt("category_id") + ";");

          while (queryResult2.next()) {
            inventory.setItemCategory(queryResult2.getString("category_name"));
          }
        } catch (Exception e) {
          e.printStackTrace();
        }

        inventoryList.add(inventory);

      }

      itemIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
      itemNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
      itemDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
      itemPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
      itemImageTableColumn.setCellValueFactory(new PropertyValueFactory<>("itemImage"));
      itemCategoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

      inventoryTableView.setItems(inventoryList);

      FilteredList<Inventory> filteredData = new FilteredList<>(inventoryList, b -> true);
      searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(inventory -> {
          if (newValue.isEmpty() || newValue == null) {
            return true;
          }
          String lowerCaseFilter = newValue.toLowerCase();
          if (inventory.getItemName().toLowerCase().contains(lowerCaseFilter)) {
            return true;
          } else if (inventory.getItemDescription().toLowerCase().contains(lowerCaseFilter)) {
            return true;
          } else if (inventory.getItemCategory().toLowerCase().contains(lowerCaseFilter)) {
            return true;
          }
          return false;
        });
      });

      SortedList<Inventory> sortedData = new SortedList<>(filteredData);
      sortedData.comparatorProperty().bind(inventoryTableView.comparatorProperty());

      inventoryTableView.setItems(sortedData);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
