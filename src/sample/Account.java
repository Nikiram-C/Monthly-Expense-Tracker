package sample;

public class Account {
    String accountNumber;
    String owner;

    public Account(String accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
    }

    public void setAccountNumber(String newAccountNumber) {
        this.accountNumber = newAccountNumber;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setOwner(String newOwner) {
        this.owner = newOwner;
    }

    public String getOwner() {
        return this.owner;
    }

    public String toString() {
        return this.accountNumber + ";" + this.owner;
    }
}
