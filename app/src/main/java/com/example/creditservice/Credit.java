package com.example.creditservice;

public class Credit {

    private String percent; // название
    private String minMaxAmount;  // столица
    private int imageBank; // ресурс флага
    private String nameOfBank;

    public Credit(String name, String capital, int flag, String nameOfBank){

        this.percent =name;
        this.minMaxAmount =capital;
        this.imageBank =flag;
        this.nameOfBank = nameOfBank;
    }

    public String getPercent() {
        return this.percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getMinMaxAmount() {
        return this.minMaxAmount;
    }

    public void setMinMaxAmount(String minMaxAmount) {
        this.minMaxAmount = minMaxAmount;
    }

    public int getImageBank() {
        return this.imageBank;
    }

    public void setImageBank(int imageBank) {
        this.imageBank = imageBank;
    }

    public String getNameOfBank(){
        return nameOfBank;
    }
    public void setNameOfBank(String nameOfBank){
        this.nameOfBank=nameOfBank;
    }
}