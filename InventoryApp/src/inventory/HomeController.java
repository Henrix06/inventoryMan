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
    public Button search;
    public Button btnCheckout;
    public Button btnCheckIn;
    public Button btnLogout;
    public Button SpreadSheet;
    public TextField idField;
    public TextField quantityField;
    public TextField stckName;
    public TableView<StockTable> tableHome;
    public TableColumn<StockTable,String> stkId;
    public TableColumn<StockTable,String> stkName;
    public TableColumn<StockTable,String> stkQua;
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

        ResultSet rs = null;
        try {
            rs = con.createStatement().executeQuery("select * from Stock");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                oblist.add(new StockTable(rs.getString("idStock"),rs.getString("StockName"),rs.getString("Quantity")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        stkId.setCellValueFactory(new PropertyValueFactory<>("idStock"));
        stkName.setCellValueFactory(new PropertyValueFactory<>("StockName"));
        stkQua.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        tableHome.setItems(oblist);

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

    public void EnterStockWindow(ActionEvent actionEvent) throws IOException {
        savedata();
    }

    public String savedata() {
        try {
            String st = "INSERT INTO Stock ( idStock, StockName, Quantity) VALUES (?,?,?)";
            preparedStatement = (PreparedStatement) con.prepareStatement(st);
            preparedStatement.setString(1, idField.getText());
            preparedStatement.setString(2, stckName.getText());
            preparedStatement.setString(3, quantityField.getText());
            preparedStatement.executeUpdate();

            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "Exception";
        }
    }
    public void EnterCheckOutWindow(ActionEvent actionEvent) {
    }
}