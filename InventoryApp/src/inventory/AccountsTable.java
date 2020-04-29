package inventory;

import java.util.Date;

public class AccountsTable {
    private int ReceitNumber;
    private Date DateAccesed;
    private String transactionName;
    private  int Amount;

    public  AccountsTable(int ReceitNumber,Date DateAccesed,String transactionName,int Amount){
        this.ReceitNumber = ReceitNumber;
        this.DateAccesed = DateAccesed;
        this.transactionName = transactionName;
        this.Amount = Amount;
    }

    public int getReceitNumber(){
        return ReceitNumber;
    }
    public void setReceitNumber(int ReceiptNumber){
        this.ReceitNumber = ReceiptNumber;
    }
    public Date getDateAccesed(){
        return  DateAccesed;
    }
    public void setDateAccesed(Date DateAccesed){
        this.DateAccesed= DateAccesed;
    }
    public String getTransactionName(){
        return transactionName;
    }
    public void setTransactionName(){
        this.transactionName = transactionName;
    }
    public  int getAmount(){
     return  Amount;
    }

    public void setAmount(int Amount) {
       this.Amount = Amount;
    }
}
