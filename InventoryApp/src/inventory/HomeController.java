package inventory;

import com.mysql.cj.xdevapi.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public Label ServerStatus;
    public Button btnLogout;
    public Button SpreadSheet;
    public Button btnTractions;
    public Button bntInventory;
    private ObservableList<StockTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;
    Connection con = null;
    ResultSet resultSet = null;

    public void SearchStpck(ActionEvent actionEvent) {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (con == null) {
            ServerStatus.setTextFill(Color.TOMATO);
            ServerStatus.setText("Server Error : Check");
        } else {
            ServerStatus.setTextFill(Color.GREEN);
            ServerStatus.setText("Server is up : Good to go");
        }
    }
    public HomeController() {
        con = ConnectionUtil.conDB();
    }

    public void SpreadSheetWindow(ActionEvent actionEvent) {
    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void enterTransactionsWindow(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("TransactionsWindow.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void enterInventoryWindow(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("InventoryWindow.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void enterStockWindow(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("StockWindow.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}