module TrailFood {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires jdk.compiler;
    requires atlantafx.base;

    opens trailfood;
    opens menu_page to javafx.fxml;
}
