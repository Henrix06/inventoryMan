package inventory;

public class StockTable{

    private String idStock,stockName,quantity;
     public StockTable(String idStock,String stockName,String quantity){
         this.quantity = quantity;
         this.stockName = stockName;
         this.idStock = idStock;
     }
     StockTable(){}
    public String getIdStock(){
        return idStock;
    }
    public void setIdStock(String idStock){
        this.idStock = idStock;
    }
    public String getstockName(){
        return stockName;
    }
    public void setStockName(String stockName){
        this.stockName = stockName;
    }
    public String getQuantity(){
        return quantity;
    }
    public void setQuantity(String quantity){
        this.quantity = quantity;
    }
}
