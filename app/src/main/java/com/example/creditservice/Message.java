package com.example.creditservice;

public class Message {
    public String header;
    public boolean approved;
    public String bankName;
    public String userName;

    public Message(String header, boolean approved, String idBank, String idUser){
        this.header = header;
        this.approved = approved;
        this.bankName = idBank;
        this.userName = idUser;
    }

}
