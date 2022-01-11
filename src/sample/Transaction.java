package sample;

public class Transaction {
    String accountNumber;
    String date;
    String description;
    int amount;
    String sign;
    static int money;

    public Transaction(String accountNumber, String date, String description, int amount, String sign) {
        this.accountNumber = accountNumber;
        this.date = date;
        this.sign = sign;
        this.description = description;
        this.amount = amount;
        if(this.getSign()!=null && this.getSign().equals("+"))
            Transaction.money += amount;
        else {
            Transaction.money -= amount;
            this.sign = "-";
        }
    }

    public Transaction() {
    }

    public void setAccountNumber(String newAccountNumber) {
        this.accountNumber = newAccountNumber;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public final void setDate(String newDate) {
        this.date = newDate;
    }

    public String getDate() {
        return this.date;
    }

    public final void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getDescription() {
        return this.description;
    }

    public final void setAmount(int newAmount) {
        this.amount = newAmount;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setMoney(int money){
        Transaction.money = money;
    }

    public int getMoney(){
        return money;
    }

    public final void setSign(String newSign) {
        this.sign = newSign;
    }

    public String getSign() {
        return sign;
    }

    public String toString() {
        return this.getAccountNumber() + ";" + this.getDate() + ";" + this.description + ";" + this.amount + ";" + this.sign;
    }
}

