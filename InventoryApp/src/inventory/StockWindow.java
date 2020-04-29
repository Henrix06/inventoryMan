package inventory;

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
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class StockWindow implements Initializable {
    public Button btnHome;
    public TableColumn<StockTable,String> col_Date;
    public TableColumn<StockTable,Integer> col_Quantity;
    public TableColumn<StockTable,String> col_Name;
    public TableColumn<StockTable,Integer> col_ID;
    public TableView<StockTable>stockTable;
    public Label ServerStatus;
    public Button btnAccounts;
    public Button removeStock;
    public Button enterStock;
    public TextField stkName;
    public TextField quantity;
    public DatePicker dateField;
    public ObservableList<StockTable> oblist = FXCollections.observableArrayList();
    public TextField stockId;
    public Button btnRefresh;

    PreparedStatement preparedStatement;
    Connection con;



    public StockWindow() {con = ConnectionUtil.conDB();}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (con == null) {
            ServerStatus.setTextFill(Color.TOMATO);
            ServerStatus.setText("Server Error : Check");
        } else {
            ServerStatus.setTextFill(Color.GREEN);
            ServerStatus.setText("Server is up : Good to go");
        }

        try{
            Connection con = ConnectionUtil.conDB();

            assert con != null;
            ResultSet rs = con.createStatement().executeQuery("select * from Stock");

            while(rs.next()){
                oblist.add(new StockTable(rs.getInt("idStock"),rs.getString("StockName"),rs.getInt("Quantity"),rs.getDate("TimeAccesed")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        col_ID.setCellValueFactory(new PropertyValueFactory<>("idStock"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("StockName"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("TimeAccesed"));

        stockTable.setItems(oblist);
    }

    public String Savedata(){
        try {
            LocalDate date = dateField.getValue();
            String st = "INSERT INTO Stock ( idStock, Quantity, StockName,TimeAccesed) VALUES (?,?,?,?)";
            preparedStatement = (PreparedStatement) con.prepareStatement(st);
            preparedStatement.setString(1,(stockId.getText()));
            preparedStatement.setString(2,(quantity.getText()));
            preparedStatement.setString(3, stkName.getText());
            preparedStatement.setString(4, String.valueOf(date));
            preparedStatement.executeUpdate();
            ServerStatus.setTextFill(Color.GREEN);
            ServerStatus.setText("Added Successfully");
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ServerStatus.setTextFill(Color.TOMATO);
            ServerStatus.setText(ex.getMessage());
            return "Exception";
        }
    }

    public void  Update(){
    }

    public void GoHome(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Home.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void EnterStock(ActionEvent actionEvent) {
        if(stkName.getText().isEmpty()||quantity.getText().isEmpty()||stkName.getText().isEmpty()){
            ServerStatus.setTextFill(Color.RED);
            ServerStatus.setText("Please enter Details");}
        else{
            Savedata();
            ServerStatus.setTextFill(Color.GREEN);
            ServerStatus.setText("Success");}
    }

    public void removeStock(ActionEvent actionEvent) {
    }

    public void OpenAccounts(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("TransactionsWindow.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void refreshView(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("StockWindow.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}
