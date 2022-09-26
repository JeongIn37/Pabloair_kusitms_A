package com.example.pabloair_kusitms_a;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ListOrderItem implements Parcelable { //객체 intent용 직렬화

    String id;
    String name; //주문자 이름
    String serializedNumber; //주문번호
    String station; //픽업 장소
    int weight; //주문 물품 무게
    int takeTime; //소요시간
    int onGoing; //진행상황

    //생성자
    public ListOrderItem(String name, String serializedNumber, String station, int weight, int takeTime, int onGoing) {
//        this.id=id;
        this.name = name;
        this.serializedNumber = serializedNumber;
        this.station = station;
        this.weight = weight;
        this.takeTime = takeTime;
        this.onGoing = onGoing;

    }

    public ListOrderItem() {

    }

    protected ListOrderItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        serializedNumber = in.readString();
        station = in.readString();
        weight = in.readInt();
        takeTime = in.readInt();
        onGoing = in.readInt();
    }

    //setter

    public void setId(String id) {this.id = id;}

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
    public String getId() {return id;}

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


    //객체 전달용 Parcelable

    public static final Creator<ListOrderItem> CREATOR = new Creator<ListOrderItem>() {
        @Override
        public ListOrderItem createFromParcel(Parcel in) {
            return new ListOrderItem(in);
        }

        @Override
        public ListOrderItem[] newArray(int size) {
            return new ListOrderItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(serializedNumber);
        dest.writeString(station);
        dest.writeInt(weight);
        dest.writeInt(takeTime);
        dest.writeInt(onGoing);
    }
}
