package model;

public class IndividualSummaryModel {

    private String reference;
    private String timestamp;
    private String name;
    private String emailAddress;
    private String userID;

    public void setReference(String reference) { this.reference = reference; }
    public String getReference(){ return this.reference; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getTimestamp(){ return this.timestamp; }

    public void setName(String name) { this.name = name; }
    public String getName(){ return this.name; }

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public String getEmailAddress(){ return this.emailAddress; }

    public void setUserID(String userID) { this.userID = userID; }
    public String getUserID(){ return this.userID; }

    public IndividualSummaryModel(String reference, String timestamp, String name, String emailAddress, String userID)
    {
        this.reference = reference;
        this.timestamp = timestamp;
        this.name = name;
        this.emailAddress = emailAddress;
        this.userID = userID;
    }
}
