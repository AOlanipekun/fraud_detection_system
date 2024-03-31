package org.arhdeez;

public record Transaction(long timestamp,
                          double amount,
                          String userID,
                          String serviceID) {

    public long getTimestamp() {
        return this.timestamp;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getServiceID() {
        return this.serviceID;
    }



}
