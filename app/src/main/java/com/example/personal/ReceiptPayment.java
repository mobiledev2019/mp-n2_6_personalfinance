package com.example.personal;

import java.util.Arrays;

public class ReceiptPayment {
    private String Id;
    private String accountCA;
    private String typeCA;
    private String amountCA;
    private String reasonCA;
    private String groupCA;
    private String statusCA;
    private String dateCA;
    private byte[] imageBill;

    public byte[] getImageBill() {
        return imageBill;
    }

    public void setImageBill(byte[] imageBill) {
        this.imageBill = imageBill;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

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
        return "ReceiptPayment{" +
                "Id='" + Id + '\'' +
                ", accountCA='" + accountCA + '\'' +
                ", typeCA='" + typeCA + '\'' +
                ", amountCA='" + amountCA + '\'' +
                ", reasonCA='" + reasonCA + '\'' +
                ", groupCA='" + groupCA + '\'' +
                ", statusCA='" + statusCA + '\'' +
                ", dateCA='" + dateCA + '\'' +
                ", imageBill=" + Arrays.toString(imageBill) +
                '}';
    }
}
