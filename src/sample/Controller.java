package sample;

import java.util.regex.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.PieChart;

import alerts.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

public class Controller implements Initializable {
    int del=0;
    public TextField accountNumber;
    public TextField owner;
    public TextField transactionDescription;
    public TextField transactionAmount;
    public Button TransactionChart;
    public Button addAccount;
    public Button addTransaction;
    public Button deleteTransaction;
    public ComboBox accountMenu;
    public TableView<Transaction> tableTransactions;
    public DatePicker transactionDate;
    public Label totalSalary;
    public TextField timeDescription;
    public Button timeButton;
    public TextField timeMessage;

    public Button addNegative;

    public ComboBox filterMenu;

    @FXML
    private PieChart balanceChartPositive;
    @FXML
    private PieChart balanceChartNegative;


    List<Account> listAccounts;
    List<Transaction> loadTransactions = files.loadTransactions();
    List<Transaction> load2Transactions;
    List<Transaction> filterTransaction;
    alertMsgs alert = new alertMsgs();
    Transaction t1 = new Transaction();

    public Controller() throws IOException {
    }

    public void initialize(URL url, ResourceBundle rb) {

        //loading existing accounts
        try {
            listAccounts = files.loadAccounts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        accountMenu.getItems().addAll( listAccounts );
        filterMenu.getItems().add("Normal");
        filterMenu.getItems().add("Income");
        filterMenu.getItems().add("Expenditure");
        filterMenu.setValue("Normal");

        //date column
        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setMinWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("date"));

        //description column
        TableColumn descriptionColumn = new TableColumn("Description");
        descriptionColumn.setMinWidth(200);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));

        //amount column
        TableColumn amountColumn = new TableColumn("Amount");
        amountColumn.setMinWidth(200);
        amountColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("amount"));

        //adding columns to the table
        tableTransactions.getColumns().addAll(dateColumn, descriptionColumn, amountColumn);
        totalSalary.setText(String.valueOf(0));
    }

    //on pressing add account button
    public void handleButtonClick(){
        addAccount.setOnAction(e -> {
            try {
                addAccountMenu(accountNumber, owner);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    //adding new account to the menu
    public void addAccountMenu(TextField input1, TextField input2) throws IOException {
        String res;
        res = input1.getText() + ";" + input2.getText();
        if (input1.getText().isEmpty())
            alert.showError( "Error!", "Account number is required." );
        else if(input2.getText().isEmpty())
            alert.showError( "Error!", "Owner name is required." );
        else if(accountMenu.getItems().contains(res))
            alert.showError( "Error!", "Given details are already present." );
        else if(accountMenu.getItems().contains(res))
            alert.showError("Error!", "Given details of account already exist");
        else {
            System.out.println(accountMenu.getItems() + res);
            //adding new account to account.txt and account menu
            Account newAccount = new Account(
                    accountNumber.getText(),
                    owner.getText()
            );
            listAccounts.add(
                    newAccount
            );
            files.saveAccounts( listAccounts );
            accountMenu.getItems().add( newAccount );
        }

    }

    //adding transaction for the earned money
    public void addButtonClicked() throws IOException {
        String string;
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(transactionAmount.getText());
        if(transactionDate.getValue() == null)
            alert.showError( "Error!", "Transaction's date is required." );
        else if(transactionDescription.getText().isEmpty())
            alert.showError( "Error!", "Transaction's description is required." );
        else if(transactionAmount.getText().isEmpty())
            alert.showError( "Error!", "Transaction's amount is required." );
        else if(!matcher.matches())
            alert.showError( "Error!", "given amount does not exist, please enter again");
        else {
            Transaction tran1 = new Transaction();
            tran1.setDate(transactionDate.getValue().toString());
            tran1.setDescription(transactionDescription.getText());
            tran1.setAmount(Integer.parseInt(transactionAmount.getText()));
            tran1.setSign("+");
            string = accountMenu.getValue().toString();
            string = string.split(";")[0];
            tran1.setAccountNumber(string);

            //adding the new transaction to the table
            tableTransactions.getItems().add(tran1);
            Transaction.money += tran1.getAmount();
            totalSalary.setText(String.valueOf(tran1.getMoney()));

            //saving data to transactions.txt
            loadTransactions.add(tran1);
            files.saveTransactions(loadTransactions);

            //clearing the text fields
            transactionDate.setValue(null);
            transactionDescription.clear();
            transactionAmount.clear();
        }
    }


    //adding transaction for deducted money
    public void addNegativeButtonClicked() throws IOException {
        if(transactionDate.getValue() == null)
            alert.showError( "Error!", "Transaction's date is required." );
        else if(transactionDescription.getText().isEmpty())
            alert.showError( "Error!", "Transaction's description is required." );
        else if(transactionAmount.getText().isEmpty())
            alert.showError( "Error!", "Transaction's amount is required." );
        else {
            Transaction tran2 = new Transaction();
            tran2.setDate(transactionDate.getValue().toString());
            tran2.setDescription(transactionDescription.getText());
            tran2.setAmount(Integer.parseInt(transactionAmount.getText()));
            tran2.setSign("-");
            String string = accountMenu.getValue().toString();
            string = string.split(";")[0];
            tran2.setAccountNumber(string);

            //adding the transaction
            tableTransactions.getItems().add(tran2);
            Transaction.money -= tran2.getAmount();
            totalSalary.setText(String.valueOf(tran2.getMoney()));

            //saving data to transactions.txt
            loadTransactions.add(tran2);
            files.saveTransactions(loadTransactions);

            //clearing text fields
            transactionDate.setValue(null);
            transactionDescription.clear();
            transactionAmount.clear();
        }
    }

    //deleting a selected transaction
    public void deleteButtonClicked() throws IOException {
        Transaction transaction = tableTransactions.getSelectionModel().getSelectedItem();
        String string = accountMenu.getValue().toString();
        string = string.split(";")[0];
        transaction.setAccountNumber(string);

        //delete the selected transaction in transaction.txt and save the file
        files.saveTransactions2(transaction);
        transaction.setMoney(0);
        loadTransactions = files.loadTransactions2(string);
        totalSalary.setText(String.valueOf(transaction.getMoney()));

        //removing the selected transaction from tableview
        tableTransactions.getItems().removeAll(tableTransactions.getSelectionModel().getSelectedItem());
    }

    //displaying respective selected account transactions
    public void clicked() throws IOException {
        filterMenu.setValue("Normal");
        t1.setMoney(0);
        String str = accountMenu.getSelectionModel().getSelectedItem().toString();
        str = str.split(";")[0];

        tableTransactions.getItems().clear();
        load2Transactions = files.loadTransactions2(str);
        tableTransactions.getItems().addAll(load2Transactions);
        totalSalary.setText(String.valueOf(t1.getMoney()));

    }

    public void chartButtonClicked() {
        TransactionChart.setOnAction(e -> {

            try {

                ObservableList<PieChart.Data> pieChartDataPositive = FXCollections.observableArrayList();
                ObservableList<PieChart.Data> pieChartDataNegative = FXCollections.observableArrayList();
                HashMap<String, Float> hashChart = new HashMap<>();

                load2Transactions.forEach(transaction -> {

                    if (!hashChart.isEmpty() &&
                            hashChart.containsKey(transaction.getDescription())
                    ) {

                        hashChart.replace(transaction.getDescription(), hashChart.get(transaction.getDescription()), hashChart.get(transaction.getDescription()) + transaction.getAmount());

                    } else {

                        hashChart.put(transaction.getDescription(), (float) transaction.getAmount());

                    }

                });

                hashChart.forEach((keyHash, valueHash) -> {

                    float amount = valueHash;
                    if (amount < 0) {
                        amount = amount * -1;
                        pieChartDataNegative.add(new PieChart.Data(keyHash, amount));
                    } else {
                        pieChartDataPositive.add(new PieChart.Data(keyHash, amount));
                    }


                });
                balanceChartPositive.setTitle("Income");
                //balanceChartNegative.setTitle("Expenses");
                balanceChartPositive.setData(pieChartDataPositive);
                balanceChartNegative.setData(pieChartDataNegative);

            } catch (NullPointerException ex) {
                Logger
                        .getLogger(alertMsgs.class.getName())
                        .log(Level.SEVERE, null, ex);
                alertMsgs.showError(alertMsgs.class.getName(), ex.getMessage());
            }
        });
    }

    public void filterClicked() throws IOException {
        String str = filterMenu.getSelectionModel().getSelectedItem().toString();
        String accStr = accountMenu.getSelectionModel().getSelectedItem().toString();
        accStr = accStr.split(";")[0];
        if(str.equals("Income")){
            tableTransactions.getItems().clear();
            Transaction.money = 0;
            filterTransaction = files.loadFilterTransactions(accStr, "+");
            tableTransactions.getItems().addAll(filterTransaction);
            totalSalary.setText(String.valueOf(t1.getMoney()));
        }
        else if(str.equals("Expenditure")){
            tableTransactions.getItems().clear();
            Transaction.money = 0;
            filterTransaction = files.loadFilterTransactions(accStr, "-");
            tableTransactions.getItems().addAll(filterTransaction);
            totalSalary.setText(String.valueOf(t1.getMoney()));

        }
        else{
            clicked();
        }
    }

    public void timeButtonClick(){
        timeButton.setOnAction(e -> {
            try {
                AlertButton();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
    private void AlertButton() throws IOException{
        HBox hb = new HBox(12);
        Timer tm = new java.util.Timer();
        del = Integer.parseInt(timeDescription.getText());
        tm.schedule(new subtimer(), del*1000);
    }
    //subclass that extends the TimerTask
    private class subtimer extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(() -> {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Expense Alert");
                al.setHeaderText("Enter the expenses");
                String m=timeMessage.getText();
                String c;
                if (del == 1) {
                    c = m+"\n1 day elapsed";
                } else
                {
                    c = String.format(m+"\n%d days elapsed",
                            del);
                }
                al.setContentText(c);
                al.showAndWait();
            });
        }
    }
}

