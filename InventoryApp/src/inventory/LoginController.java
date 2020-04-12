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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

import static com.sun.webkit.dom.EventImpl.SELECT;

public class LoginController implements Initializable {
    public Label lblOne;
    public Button btnSingUp;
    public Button btnSignIn;
    public PasswordField passwordField;
    public TextField input;
    public Label lblOne1;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public void Login(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnSignIn) {
            //login here
            if (Login().equals("Success")) {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) actionEvent.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Home.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }


    public void Register(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("RegisterWindow.fxml")));
        stage.setScene(scene);
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (con == null) {
            lblOne.setTextFill(Color.TOMATO);
            lblOne.setText("Server Error : Check");
        } else {
            lblOne.setTextFill(Color.GREEN);
            lblOne.setText("Server is up : Good to go");
        }
    }
    public LoginController() {
        con = ConnectionUtil.conDB();
    }

    public String Login(){
        String status = "Success";
        String email   = input.getText();
        String username = input.getText();
        String password = passwordField.getText();

        if (username.isEmpty() && password.isEmpty()){
          setLblOne(Color.valueOf("RED"),"Empty Credentials");
          status = "No cred";
        }
        else {
            String sql  = "SELECT * FROM user where username=? and password =?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                resultSet = preparedStatement.executeQuery();
                if(!resultSet.next()){
                    setLblOne(Color.valueOf("RED"),"Enter Correct Credentials");
                    status = "Error";
                }
                else {
                    setLblOne(Color.valueOf("RED"),"Login Successful");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                status ="Exception";
            }
        }

        return status;
    }
    void setLblOne(Color color,String text){
        lblOne1.setText(text);
        lblOne1.setTextFill(color);
        System.out.println(text);
    }

}
