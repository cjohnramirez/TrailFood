package trailfood;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private ImageView logoImageView;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField registerPasswordField;
    @FXML
    private PasswordField confirmregisterPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File logoFile = new File("assets/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        File brandingFile = new File("assets/logo.png");
        Image brandingImage = new Image(logoFile.toURI().toString());
        brandingImageView.setImage(logoImage);
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void registerButtonInAction(ActionEvent event) {
        if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty() && !userNameField.getText().isEmpty() && !registerPasswordField.getText().isEmpty() && !confirmregisterPasswordField.getText().isEmpty()){
            registerUser();
        } else {
            registerMessageLabel.setText("Please complete the form.");
        }
    }

    public void loginButtonInAction(ActionEvent event) {
        try {
            MainApplication.changeScene("Login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void registerUser(){
        if (!registerPasswordField.getText().equals(confirmregisterPasswordField.getText())){
            registerMessageLabel.setText("Password does not match");
        } else {
            DatabaseConnector connectNow = new DatabaseConnector();
            Connection connectDB = connectNow.getConnection();

            String insertToRegister = String.format("INSERT INTO user_account (first_name, last_name, user_name, password) VALUES ('%s', '%s', '%s', '%s')", firstNameField.getText(), lastNameField.getText(), userNameField.getText(), registerPasswordField.getText());

            try {
                Statement statement = connectDB.createStatement();
                int queryResult = statement.executeUpdate(insertToRegister);

                registerMessageLabel.setText("User has been registered successfully");

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }
    }
}
