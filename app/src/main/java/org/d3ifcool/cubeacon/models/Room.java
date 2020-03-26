package org.d3ifcool.cubeacon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {

    private String name;
    private String desc;
    private String floor;
    private int photo;

    public Room() {
    }

    public Room(String name, String desc, String floor, int photo) {
        this.name = name;
        this.desc = desc;
        this.floor = floor;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.desc);
        dest.writeString(this.floor);
        dest.writeInt(this.photo);
    }

    protected Room(Parcel in) {
        this.name = in.readString();
        this.desc = in.readString();
        this.floor = in.readString();
        this.photo = in.readInt();
    }

    public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel source) {
            return new Room(source);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };
}
