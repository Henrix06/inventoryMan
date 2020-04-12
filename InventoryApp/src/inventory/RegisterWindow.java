package inventory;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterWindow  implements Initializable {

    public Label lblSaveStatus;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public Button btnRegister;
    public Button btnBack;
    public PasswordField passwordField;
    public TextField usrField;
    public TextField emailField;
    public PasswordField verifyPassword;
    public Label lblError;

    public void PrevWindow(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void Register(ActionEvent actionEvent) {
        if (passwordField.getText().isEmpty() && usrField.getText().isEmpty()){
        lblSaveStatus.setTextFill(Color.RED);
        lblSaveStatus.setText("Please enter Details");
        }
    else {if (passwordField.getText().equals(verifyPassword.getText())){
            saveData();
            lblSaveStatus.setTextFill(Color.GREEN);
            lblSaveStatus.setText("Added Successfully");
        } else {
            lblSaveStatus.setTextFill(Color.TOMATO);
            lblSaveStatus.setText("passwords Don't match");}}}



    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (con == null) {
            lblError.setTextFill(Color.TOMATO);
            lblError.setText("Server Error : Check");
        } else {
            lblError.setTextFill(Color.GREEN);
            lblError.setText("Server is up : Good to go");
        }
    }
    public RegisterWindow() {
        con = ConnectionUtil.conDB();
    }

    private String saveData() {

        try {
            String st = "INSERT INTO user ( email, password, username) VALUES (?,?,?)";
            preparedStatement = (PreparedStatement) con.prepareStatement(st);
            preparedStatement.setString(1, emailField.getText());
            preparedStatement.setString(2, passwordField.getText());
            preparedStatement.setString(3, usrField.getText());
            preparedStatement.executeUpdate();
            lblSaveStatus.setTextFill(Color.GREEN);
            lblSaveStatus.setText("Added Successfully");

            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblSaveStatus.setTextFill(Color.TOMATO);

            lblSaveStatus.setText(ex.getMessage());
            return "Exception";
        }
    }
}
