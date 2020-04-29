package inventory;

import java.lang.reflect.InvocationHandler;
import java.util.Date;

public class InventoryTable {
    private int idInventory;
    private int Quantity;
    private String Name;
    private Date TimeAccesed;
    
    public InventoryTable(int idInventory,String Name,int Quantity,Date TimeAccesed){
       this.idInventory = idInventory;
       this.Name = Name;
       this.Quantity = Quantity;
       this.TimeAccesed = TimeAccesed;
    }
    InventoryTable(){}
    public int getIdInventory(){
        return idInventory;
    }
    public void setIdInventory(int idInventory){
        this.idInventory = idInventory;
    }
    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public int getQuantity(){
        return Quantity;
    }
    public void setQuantity(int Quantity){
        this.Quantity = Quantity;
    }
    public Date getTimeAccesed() {
        return TimeAccesed;
    }
    public void setTimeAccesed(Date TimeAccesed) {
        this.TimeAccesed = TimeAccesed;
    }
}

