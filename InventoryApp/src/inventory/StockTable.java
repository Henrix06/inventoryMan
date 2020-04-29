package inventory;

import java.util.Date;

public class StockTable{

    private int idStock;
    private String StockName;
    private int Quantity;
    private Date TimeAccesed;
     public StockTable(int idStock,String StockName,int Quantity,Date TimeAccesed){
         this.idStock = idStock;
         this.StockName = StockName;
         this.Quantity = Quantity;
         this.TimeAccesed = TimeAccesed;
     }
     StockTable(){}


    public int getIdStock(){
        return idStock;
    }
    public void setIdStock(int idStock){
        this.idStock = idStock;
    }
    public String getStockName(){
        return StockName;
    }
    public void setStockName(String StockName){
        this.StockName = StockName;
    }
    public int getQuantity(){
        return Quantity;
    }
    public void setQuantity(int Quantity){
        this.Quantity = Quantity;
    }
    public Date getTimeAccesed(){
         return TimeAccesed;
     }
    public void setTimeAccesed(Date TimeAccessed){
         this.TimeAccesed = TimeAccesed;
    }
}
