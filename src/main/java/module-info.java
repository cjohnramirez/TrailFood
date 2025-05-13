module TrailFood {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;
    requires jdk.compiler;

    opens trailfood;
    opens menu_page to javafx.fxml;
}
