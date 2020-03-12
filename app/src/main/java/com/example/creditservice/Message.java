package com.example.creditservice;

public class Message {
    public String header;
    public boolean approved;
    public int idBank;
    public int idUser;

    public Message(String header, boolean approved, int idBank, int idUser){
        this.header = header;
        this.approved = approved;
        this.idBank = idBank;
        this.idUser = idUser;
    }

}
