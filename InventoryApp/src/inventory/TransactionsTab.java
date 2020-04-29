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

public class TransactionsTab implements Initializable {

    public Button btnHome;
    public TextField receiptField;
    public TextField nameField;
    public Button btnTransact;
    public Label serverStatus;
    public DatePicker dateField;
    public Button btnRefresh;
    public TableView<AccountsTable>transTable;
    public TableColumn<AccountsTable,Integer>col_Number;
    public TableColumn<AccountsTable, Date> col_Date;
    public TableColumn<AccountsTable,String> c0l_Name;
    public TableColumn<AccountsTable,Integer> col_Amount;
    public TextField amountField;
    public TextField totalField;
    public ObservableList<AccountsTable> oblist = FXCollections.observableArrayList();
    PreparedStatement preparedStatement;
    Connection connection;
    Connection con = null;
    ResultSet resultSet = null;

    public TransactionsTab(){
        con = ConnectionUtil.conDB();
    }

    @Override
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
                ResultSet rs = con.createStatement().executeQuery("select * from Accounts");

                while(rs.next()){
                    oblist.add(new AccountsTable(rs.getInt("ReceitNumber"), rs.getDate("DateAccesed"), rs.getString("transactionName"), rs.getInt("Amount")));
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            col_Amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
            c0l_Name.setCellValueFactory(new PropertyValueFactory<>("transactionName"));
            col_Number.setCellValueFactory(new PropertyValueFactory<>("ReceitNumber"));
            col_Date.setCellValueFactory(new PropertyValueFactory<>("DateAccesed"));

            transTable.setItems(oblist);}
    public String Savedata(){
        try {
            LocalDate date = dateField.getValue();
            String st = "INSERT INTO Accounts ( ReceitNumber, Amount, transactionName,DateAccesed) VALUES (?,?,?,?)";
            preparedStatement = (PreparedStatement) con.prepareStatement(st);
            preparedStatement.setString(1,(receiptField.getText()));
            preparedStatement.setString(2,(amountField.getText()));
            preparedStatement.setString(3, nameField.getText());
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


    public void goHome(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Home.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void enterTransaction(ActionEvent actionEvent) {
        if(nameField.getText().isEmpty()||receiptField.getText().isEmpty()||amountField.getText().isEmpty()) {
            serverStatus.setTextFill(Color.TOMATO);
            serverStatus.setText("Please Enter Details");
        }
        else{
        Savedata();
    }}

    public void refreshWindow(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("TransactionsWindow.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}
