package com.example.creditservice;

public class Message {
    public String header;
    public boolean approved;
    public String bankName;
    public String userName;
    public int idMessage;
    public Message(String header, boolean approved, String idBank, String idUser, int idMessage){
        this.header = header;
        this.approved = approved;
        this.bankName = idBank;
        this.userName = idUser;
        this.idMessage = idMessage;
    }

}
