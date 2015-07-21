package model;

import java.util.List;

public class AccountDetails {

    public String accountName;
    public String accountHolder;
    public String accountType;
    public String activityAvailableFrom;
    public String accountNumber;
    public String sortCode;
    public String balance;
    public String balanceFormatted;
    public List<Transaction> transactions;
    public String currencyCode;
    public String verifiedOn;

    public String getAccountName(){ return this.accountName; }
    public void setAccountName(String accountName){ this.accountName = accountName; }

    public String getAccountHolder(){ return this.accountHolder; }
    public void setAccountHolder(String accountHolder){ this.accountHolder = accountHolder; }

    public String getAccountType(){ return this.accountType; }
    public void setAccountType(String accountType){ this.accountType = accountType; }

    public String getActivityAvailableFrom(){ return this.activityAvailableFrom; }
    public void setActivityAvailableFrom(String activityAvailableFrom){ this.activityAvailableFrom = activityAvailableFrom; }

    public String getAccountNumber(){ return this.accountNumber; }
    public void setAccountNumber(String accountNumber){ this.accountNumber = accountNumber; }

    public String getSortCode(){ return this.sortCode; }
    public void setSortCode(String sortCode){ this.sortCode = sortCode; }

    public String getBalance(){ return this.balance; }
    public void setBalance(String balance){ this.balance = balance; }

    public String getBalanceFormatted(){ return this.balanceFormatted; }
    public void setBalanceFormatted(String date){ this.balanceFormatted = balanceFormatted; }

    public List<Transaction> getTransactions(){ return this.transactions; }
    public void setTransactions(List<Transaction> transactions){ this.transactions = transactions; }

    public String getCurrencyCode(){ return this.currencyCode; }
    public void setCurrencyCode(String currencyCode){ this.currencyCode = currencyCode; }

    public String getVerifiedOn(){ return this.verifiedOn; }
    public void setVerifiedOn(String verifiedOn){ this.verifiedOn = verifiedOn; }

    public AccountDetails(String accountName, String accountHolder, String accountType, String activityAvailableFrom, String accountNumber, String sortCode, String balance, String balanceFormatted, List<Transaction> transactions, String currencyCode, String verifiedOn)
    {
        this.accountName = accountName;
        this.accountHolder = accountHolder;
        this.accountType = accountType;
        this.activityAvailableFrom = activityAvailableFrom;
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.balance = balance;
        this.balanceFormatted = balanceFormatted;
        this.transactions = transactions;
        this.currencyCode = currencyCode;
        this.verifiedOn = verifiedOn;
    }
}
