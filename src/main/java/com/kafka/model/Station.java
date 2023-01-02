package com.kafka.model;



public class Station {


    private Integer number;

    private String contractName;

    private String name;

    private String address;

    private Boolean banking;

    private Boolean bonus;

    private Integer bikeStands;

    private Integer availableBikeStands;

    private Integer availableBikes;

    private String status;

    private Long lastUpdate;

    public Station() {
    }


    public Integer getNumber() {
        return number;
    }


    public void setNumber(Integer number) {
        this.number = number;
    }


    public String getContractName() {
        return contractName;
    }


    public void setContractName(String contractName) {
        this.contractName = contractName;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public Boolean getBanking() {
        return banking;
    }

    public void setBanking(Boolean banking) {
        this.banking = banking;
    }


    public Boolean getBonus() {
        return bonus;
    }


    public void setBonus(Boolean bonus) {
        this.bonus = bonus;
    }


    public Integer getBikeStands() {
        return bikeStands;
    }


    public void setBikeStands(Integer bikeStands) {
        this.bikeStands = bikeStands;
    }


    public Integer getAvailableBikeStands() {
        return availableBikeStands;
    }


    public void setAvailableBikeStands(Integer availableBikeStands) {
        this.availableBikeStands = availableBikeStands;
    }


    public Integer getAvailableBikes() {
        return availableBikes;
    }


    public void setAvailableBikes(Integer availableBikes) {
        this.availableBikes = availableBikes;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public Long getLastUpdate() {
        return lastUpdate;
    }


    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Station{" +
                "number=" + number +
                ", contractName='" + contractName + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", banking=" + banking +
                ", bonus=" + bonus +
                ", bikeStands=" + bikeStands +
                ", availableBikeStands=" + availableBikeStands +
                ", availableBikes=" + availableBikes +
                ", status='" + status + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
