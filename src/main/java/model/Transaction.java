package model;

public class Transaction {
    public String date;
    public String description;
    public String amount;
    public String type;

    public String getDate(){ return this.date; }
    public void setDate(String date){ this.date = date; }

    public String getDescription(){ return this.description; }
    public void setDescription(String description){ this.description = description; }

    public String getAmount(){ return this.amount; }
    public void setAmount(String amount){ this.amount = amount; }

    public String getType(){ return this.type; }
    public void setType(String type){ this.type = type; }

    public Transaction(String date, String description, String amount, String type)
    {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }
}
