package com.example.pabloair_kusitms_a;

public class ListOrderItem {

    String id;
    String name; //주문자 이름
    String serializedNumber; //주문번호
    String station; //픽업 장소
    int weight; //주문 물품 무게
    int takeTime; //소요시간
    int onGoing; //진행상황

    //생성자
    public ListOrderItem(String name, String serializedNumber, String station, int weight, int takeTime, int onGoing) {
        this.name = name;
        this.serializedNumber = serializedNumber;
        this.station = station;
        this.weight = weight;
        this.takeTime = takeTime;
        this.onGoing = onGoing;

    }

    public ListOrderItem() {

    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    public void setOnGoing(int onGoing) {this.onGoing = onGoing;}

    public void setSerializedNumber(String serializedNumber) {
        this.serializedNumber = serializedNumber;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setTakeTime(int takeTime) {
        this.takeTime = takeTime;
    }


    //getter
    public String getName() {
        return name;
    }

    public String getSerializedNumber() {
        return serializedNumber;
    }

    public String getStation() {
        return station;
    }

    public int getWeight() {
        return weight;
    }

    public int getTakeTime() {
        return takeTime;
    }

    public int getOnGoing() {return onGoing;}

}
