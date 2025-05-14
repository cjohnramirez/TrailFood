package menu_page;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import trailfood.DatabaseConnector;
import trailfood.MainApplication;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuPageController implements Initializable {
  @FXML
  private GridPane topGrid;

  @FXML
  private GridPane bottomGrid;

  @FXML
  private VBox orderVBox;

  private final List<Category> categories = new ArrayList<>();

  private MenuPageListener menuPageListener;

  private OrderController orderController = null;

  private List<Category> getCategoryData() {
    List<Category> categories = new ArrayList<>();
    Category category;

    DatabaseConnector connectNow = new DatabaseConnector();
    Connection connectDB = connectNow.getConnection();

    String fetchCategories = "SELECT * FROM category;";
    String countItemsPerCategory = "SELECT COUNT(*) AS total_count FROM menu_item WHERE category_id=\"";

    try (
        Statement statement1 = connectDB.createStatement();
        Statement statement2 = connectDB.createStatement();
        ResultSet queryResult = statement1.executeQuery(fetchCategories)) {
      while (queryResult.next()) {
        category = new Category();
        category.setName(queryResult.getString("category_name"));
        category.setImage(queryResult.getString("category_image"));

        ResultSet totalQuantity = statement2
            .executeQuery(countItemsPerCategory + queryResult.getInt("category_id") + "\";");
        if (totalQuantity.next()) {
          category.setQuantity(totalQuantity.getString("total_count"));
        }

        category.setColor("#98d6f2");
        categories.add(category);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return categories;
  }

  private List<MenuItem> getMenuItemData(Category category) {
    List<MenuItem> items = new ArrayList<>();

    DatabaseConnector connectNow = new DatabaseConnector();
    Connection connectDB = connectNow.getConnection();

    String fetchCategory = "SELECT * FROM category WHERE category_name=\"" + category.getName() + "\";";

    try (
        Statement statement1 = connectDB.createStatement();
        Statement statement2 = connectDB.createStatement();
        ResultSet queryCategoryId = statement1.executeQuery(fetchCategory)) {
      while (queryCategoryId.next()) {
        String fetchMenuItems = "SELECT * FROM menu_item WHERE category_id="
            + queryCategoryId.getInt("category_id");
        ResultSet queryResult = statement2.executeQuery(fetchMenuItems);

        while (queryResult.next()) {
          MenuItem menuItem = new MenuItem();
          menuItem.setName(queryResult.getString("item_name"));
          menuItem.setPrice(queryResult.getString("item_price"));
          menuItem.setImage(queryResult.getString("item_image"));
          items.add(menuItem);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return items;
  }

  public void initialize(URL url, ResourceBundle resourceBundle) {
    categories.addAll(getCategoryData());
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(
          MainApplication.class.getResource("/trailfood/menu_page/Order.fxml"));
      BorderPane borderPane = fxmlLoader.load();
      orderController = fxmlLoader.getController();
      orderVBox.getChildren().add(borderPane);
    } catch (Exception e) {
      e.printStackTrace();
    }

    final OrderController finalOrderController = orderController;

    if (!categories.isEmpty()) {
      menuPageListener = category -> {
        try {
          bottomGrid.getChildren().clear();
          List<MenuItem> categoryItems = getMenuItemData(category);
          int col = 0;
          int row = 0;

          for (MenuItem item : categoryItems) {
            FXMLLoader fxmlLoader = new FXMLLoader(
                MainApplication.class.getResource("/trailfood/menu_page/MenuItem.fxml"));
            VBox vBox = fxmlLoader.load();

            MenuItemListener menuItemListener = new MenuItemListener() {
              int quantity = 0;
              boolean isDeleted = false;

              @Override
              public void incrementQuantityListener(MenuItem menuItem) {
                if (quantity >= 0) {
                  quantity++;
                  finalOrderController.updateOrderItem(menuItem, quantity);
                }
              }

              @Override
              public void decrementQuantityListener(MenuItem menuItem) {
                if (quantity > 1) {
                  quantity--;
                  finalOrderController.updateOrderItem(menuItem, quantity);
                } else {
                  finalOrderController.deleteOrderItem(menuItem);
                  isDeleted = true;
                } 

                if (isDeleted) {
                  System.out.println("Item is deleted!");
                  isDeleted = false;
                } 
              }
            };

            MenuItemController menuItemController = fxmlLoader.getController();
            menuItemController.setData(item, menuItemListener);

            if (col == 3) {
              col = 0;
              row++;
            }

            bottomGrid.add(vBox, col++, row);
            bottomGrid.setVgap(20);
            bottomGrid.setHgap(10);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      };
    }

    int col = 0;
    int row = 0;

    try {
      for (Category category : categories) {
        FXMLLoader fxmlLoader = new FXMLLoader(
            MainApplication.class.getResource("/trailfood/menu_page/Category.fxml"));
        VBox vBox = fxmlLoader.load();

        CategoryController categoryController = fxmlLoader.getController();
        categoryController.setData(category, menuPageListener);

        if (col == 4) {
          col = 0;
          row++;
        }

        topGrid.add(vBox, col++, row);
        topGrid.setVgap(10);
        topGrid.setHgap(10);
        topGrid.setGridLinesVisible(false);

        GridPane.setHgrow(vBox, Priority.ALWAYS);
        GridPane.setVgrow(vBox, Priority.ALWAYS);
        vBox.setMaxWidth(Double.MAX_VALUE);
        vBox.setMaxHeight(Double.MAX_VALUE);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
