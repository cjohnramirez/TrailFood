package menu_page;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.InputStream;

public class CategoryController {
    @FXML
    private Label categoryLabel;

    @FXML
    private Label categoryQuantityLabel;

    @FXML
    private ImageView categoryImage;

    @FXML
    private void click(MouseEvent mouseEvent){
        categoryListener.onClickListener(category);
    }

    private Category category;
    private CategoryListener categoryListener;

    public void setData(Category category, CategoryListener categoryListener) {
        this.category = category;
        this.categoryListener = categoryListener;
        categoryLabel.setText(category.getName());
        categoryQuantityLabel.setText(category.getQuantity() + " items");

        //fail-safe for any files reading, use this
        InputStream is = getClass().getResourceAsStream(category.getImage());
        if (is == null) {
            System.err.println("Image not found at: " + category.getImage());
        } else {
            categoryImage.setImage(new Image(is));
        }
    }
}
