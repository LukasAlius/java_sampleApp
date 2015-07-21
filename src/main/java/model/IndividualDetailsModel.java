package model;

import java.util.List;

public class IndividualDetailsModel {
    public String reference;
    public String provider;
    public List<AccountDetails> accounts;

    public String getReference(){ return this.reference; }
    public void setReference(String reference){ this.reference = reference; }

    public String getProvider(){ return this.provider; }
    public void setProvider(String provider){ this.provider = provider; }

    public List<AccountDetails> getAccounts(){ return this.accounts; }
    public void setAccounts(List<AccountDetails> accounts){ this.accounts = accounts; }

    public IndividualDetailsModel(String reference, String provider, List<AccountDetails> accounts )
    {
        this.reference = reference;
        this.provider = provider;
        this.accounts = accounts;
    }
}

