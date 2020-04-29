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
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class InventoryWindow implements Initializable {
    public Button btnHome;
    public Button btnAccounts;
    public TextField idField;
    public TextField nameField;
    public TextField quantityField;
    public DatePicker dateField;
    public Button btnCheckIn;
    public Button btnCheckout;
    public Label serverStatus;
    public Button btnRefresh;
    public TableView<InventoryTable> inventoryTable;
    public TableColumn<InventoryTable,Integer> col_id;
    public TableColumn<InventoryTable,String> col_name;
    public TableColumn<InventoryTable,Integer> col_quantity;
    public TableColumn<InventoryTable, Date> col_time;
    public ObservableList<InventoryTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection con;
    public InventoryWindow(){con = ConnectionUtil.conDB();}
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (con == null) {
            serverStatus.setTextFill(Color.TOMATO);
            serverStatus.setText("Server Error : Check");
        } else {
            serverStatus.setTextFill(Color.GREEN);
            serverStatus.setText("Server is up : Good to go");
        }
        try{
            Connection con = ConnectionUtil.conDB();

            assert con != null;
            ResultSet rs = con.createStatement().executeQuery("select * from Inventory");
            String query = "SELECT idInventory.itrmcode,Name.item ";


            while(rs.next()){
                oblist.add(new InventoryTable(rs.getInt("idInventory"),rs.getString("Name"),rs.getInt("Quantity"),rs.getDate("TimeAccesed")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        col_id.setCellValueFactory(new PropertyValueFactory<>("idInventory"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("TimeAccesed"));

        inventoryTable.setItems(oblist);

    }

        public String Savedata () {
            try {
                LocalDate date = dateField.getValue();
                String st = "INSERT INTO Inventory ( idInventory, Name, Quantity,TimeAccesed) VALUES (?,?,?,?)";
                preparedStatement = (PreparedStatement) con.prepareStatement(st);
                preparedStatement.setString(1, (idField.getText()));
                preparedStatement.setString(2, (nameField.getText()));
                preparedStatement.setString(3, (quantityField.getText()));
                preparedStatement.setString(4, String.valueOf(date));
                preparedStatement.executeUpdate();
                serverStatus.setTextFill(Color.GREEN);
                serverStatus.setText("Added Successfully");
                return "Success";

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                serverStatus.setTextFill(Color.TOMATO);
                serverStatus.setText(ex.getMessage());
                return "Exception";
            }
        }

        public String ChangeData(){
            try {
                LocalDate date = dateField.getValue();
                String st = "UPDATE Inventory WHERE ( idInventory, Name, Quantity,TimeAccesed) VALUES (?,?,?,?)";
                preparedStatement = (PreparedStatement) con.prepareStatement(st);
                preparedStatement.setString(1, (idField.getText()));
                preparedStatement.setString(2, (nameField.getText()));
                preparedStatement.setString(3, (quantityField.getText()));
                preparedStatement.setString(4, String.valueOf(date));
                preparedStatement.executeUpdate();
                serverStatus.setTextFill(Color.GREEN);
                serverStatus.setText("Added Successfully");
                return "Success";

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                serverStatus.setTextFill(Color.TOMATO);
                serverStatus.setText(ex.getMessage());
                return "Exception";
            }
        }
        public void goHome (ActionEvent actionEvent)throws IOException{
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Home.fxml")));
            stage.setScene(scene);
            stage.show();
        }

        public void goAccounting (ActionEvent actionEvent)throws IOException {
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("TransactionsWindow.fxml")));
            stage.setScene(scene);
            stage.show();
        }

        public void EnterInv (ActionEvent actionEvent){
        if(nameField.getText().isEmpty()||quantityField.getText().isEmpty()||idField.getText().isEmpty()){
            serverStatus.setTextFill(Color.TOMATO);
            serverStatus.setText(" Error enter all details");
        }
        else {
            Savedata();
        }
        }

        public void UpdateInv (ActionEvent actionEvent){
            if(nameField.getText().isEmpty()||quantityField.getText().isEmpty()||idField.getText().isEmpty()){
                serverStatus.setTextFill(Color.TOMATO);
                serverStatus.setText(" Error enter all details");
            }
            else {
                ChangeData();
            }
        }

        public void refreshView(ActionEvent actionEvent)throws IOException{
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("InventoryWindow.fxml")));
            stage.setScene(scene);
            stage.show();
        }
    }
