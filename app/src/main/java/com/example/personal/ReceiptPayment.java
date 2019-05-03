package com.example.personal;

public class ReceiptPayment {
    private String accountCA;
    private String typeCA;
    private String amountCA;
    private String reasonCA;
    private String groupCA;
    private String statusCA;
    private String dateCA;

    public String getAccountCA() {
        return accountCA;
    }

    public void setAccountCA(String accountCA) {
        this.accountCA = accountCA;
    }

    public String getTypeCA() {
        return typeCA;
    }

    public void setTypeCA(String typeCA) {
        this.typeCA = typeCA;
    }

    public String getAmountCA() {
        return amountCA;
    }

    public void setAmountCA(String amountCA) {
        this.amountCA = amountCA;
    }

    public String getReasonCA() {
        return reasonCA;
    }

    public void setReasonCA(String reasonCA) {
        this.reasonCA = reasonCA;
    }

    public String getGroupCA() {
        return groupCA;
    }

    public void setGroupCA(String groupCA) {
        this.groupCA = groupCA;
    }

    public String getStatusCA() {
        return statusCA;
    }

    public void setStatusCA(String statusCA) {
        this.statusCA = statusCA;
    }

    public String getDateCA() {
        return dateCA;
    }

    public void setDateCA(String dateCA) {
        this.dateCA = dateCA;
    }

    @Override
    public String toString() {
        return reasonCA + "     " + amountCA  + "       " + dateCA  + "     " + accountCA;
    }
}
