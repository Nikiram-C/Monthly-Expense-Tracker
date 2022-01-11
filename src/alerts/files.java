package alerts;

import sample.Transaction;
import sample.Account;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class files {
    //return all the accounts from accounts.txt
    public static List<Account> loadAccounts() throws  IOException {

        String accountsFile = "src/accounts";
        FileReader _file = new FileReader( accountsFile );
        BufferedReader _bufferedReader = new BufferedReader( _file );
        String _lineReaded;
        String[] _splitDataAccount;
        List<Account> listAccounts = new ArrayList<>();

        _lineReaded = _bufferedReader.readLine();

        do{

            _splitDataAccount = _lineReaded.split( ";" );
            listAccounts.add(
                    new Account(
                            _splitDataAccount[ 0 ],
                            _splitDataAccount[ 1 ]
                    )
            );
            _lineReaded = _bufferedReader.readLine();

        }while( _lineReaded != null );

        return listAccounts;

    }

    public static void saveAccounts(List<Account> listAccounts) throws IOException{
        //saving the new account to account.txt
        String accountsFile = "src/accounts";
        FileWriter _file = new FileWriter( accountsFile );
        BufferedWriter _bufferedWriter = new BufferedWriter( _file );
        _bufferedWriter.flush();

        listAccounts.forEach( ( Account dataAccount ) -> {

            try {

                _bufferedWriter.write(
                        dataAccount.toString()
                );
                _bufferedWriter.newLine();

            } catch (IOException e) {

                Logger
                        .getLogger( files.class.getName() )
                        .log( Level.SEVERE, null, e );
                alertMsgs.showError( files.class.getName(), e.getMessage() );

            }

        } );

        _bufferedWriter.close();

    }

    public static List<Transaction> loadTransactions() throws IOException{
        //return all the transactions in transactions.txt
        String transactionsFile = "src/transactions.txt";
        FileReader _file = new FileReader( transactionsFile );
        BufferedReader _bufferedReader = new BufferedReader( _file );
        String _lineReaded;
        String[] _splitDataTransaction;
        List<Transaction> listTransactions = new ArrayList<>();

        _lineReaded = _bufferedReader.readLine();

        do{

            _splitDataTransaction = _lineReaded.split( ";");
            listTransactions.add(
                    new Transaction(
                            _splitDataTransaction[0],
                            _splitDataTransaction[1],
                            _splitDataTransaction[2],
                            Integer.parseInt(_splitDataTransaction[3]),
                            _splitDataTransaction[4]
                    )
            );
            _lineReaded = _bufferedReader.readLine();

        }while( _lineReaded != null );

        return listTransactions;

    }

    public static void saveTransactions(List<Transaction> listTransactions) throws IOException{
        //add new transactions to transaction.txt
        String transactionsFile = "src/transactions.txt";
        FileWriter _file = new FileWriter( transactionsFile );
        BufferedWriter _bufferedWriter = new BufferedWriter( _file );

        listTransactions.forEach( ( Transaction dataTransaction ) -> {
            //System.out.println(dataTransaction.getSign());
            try {

                _bufferedWriter.write(
                        dataTransaction.toString()
                );
                _bufferedWriter.newLine();

            } catch (IOException e) {

                Logger
                        .getLogger( files.class.getName() )
                        .log( Level.SEVERE, null, e );
                alertMsgs.showError(files.class.getName(), e.getMessage() );

            }

        } );

        _bufferedWriter.close();

    }

    public static List<Transaction> loadTransactions2(String str) throws IOException{
        //return all transactions of a selected account from transactions.txt
        String transactionsFile = "src/transactions.txt";
        FileReader _file = new FileReader( transactionsFile );
        BufferedReader _bufferedReader = new BufferedReader( _file );
        String _lineReaded;
        String[] _splitDataTransaction;
        List<Transaction> listTransactions = new ArrayList<>();

        _lineReaded = _bufferedReader.readLine();

        do{

            _splitDataTransaction = _lineReaded.split( ";");
            if(_splitDataTransaction[0].equals(str)){
                listTransactions.add(
                        new Transaction(
                                _splitDataTransaction[0],
                                _splitDataTransaction[1],
                                _splitDataTransaction[2],
                                Integer.parseInt(_splitDataTransaction[3]),
                                _splitDataTransaction[4]
                        )
                );}
            _lineReaded = _bufferedReader.readLine();

        }while( _lineReaded != null );

        return listTransactions;

    }

    public static void saveTransactions2(Transaction trans) throws IOException{
        //delete a selected transaction from transaction.txt
        File inputFile = new File("src/transactions.txt");
        File tempFile = new File("src/myTempFile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = trans.toString();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();

        //copy the contents of myTempFile.txt(tempfile) to transaction.txt(input file)
        String temp;
        BufferedReader br = new BufferedReader(new FileReader(tempFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile));
        while ((temp = br.readLine()) != null) {
            bw.write(temp);
            bw.newLine();
            bw.flush();
        }
    }

    public static List<Transaction> loadFilterTransactions(String str, String sign) throws IOException{
        //return all transactions of a selected account from transactions.txt
        String transactionsFile = "src/transactions.txt";
        FileReader _file = new FileReader( transactionsFile );
        BufferedReader _bufferedReader = new BufferedReader( _file );
        String _lineReaded;
        String[] _splitDataTransaction;
        List<Transaction> listTransactions = new ArrayList<>();

        _lineReaded = _bufferedReader.readLine();

        do{

            _splitDataTransaction = _lineReaded.split( ";");
            if(_splitDataTransaction[0].equals(str) && _splitDataTransaction[4].equals(sign)){
                listTransactions.add(
                        new Transaction(
                                _splitDataTransaction[0],
                                _splitDataTransaction[1],
                                _splitDataTransaction[2],
                                Integer.parseInt(_splitDataTransaction[3]),
                                _splitDataTransaction[4]
                        )
                );}
            _lineReaded = _bufferedReader.readLine();

        }while( _lineReaded != null );

        return listTransactions;

    }
}
