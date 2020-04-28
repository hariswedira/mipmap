package org.d3ifcool.cubeacon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {

    private String name;
    private String desc;
    private String floor;
    private String supervisor;
    private String number;
    private String type;
    private String photo;
    private String svPhoto;
    private String nip;

    public Room() {
    }

    public Room(String name, String desc, String floor, String supervisor, String number, String type, String photo, String svPhoto, String nip) {
        this.name = name;
        this.desc = desc;
        this.floor = floor;
        this.supervisor = supervisor;
        this.number = number;
        this.type = type;
        this.photo = photo;
        this.svPhoto = svPhoto;
        this.nip = nip;
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

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSvPhoto() {
        return svPhoto;
    }

    public void setSvPhoto(String svPhoto) {
        this.svPhoto = svPhoto;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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
        dest.writeString(this.supervisor);
        dest.writeString(this.number);
        dest.writeString(this.type);
        dest.writeString(this.photo);
        dest.writeString(this.svPhoto);
        dest.writeString(this.nip);
    }

    protected Room(Parcel in) {
        this.name = in.readString();
        this.desc = in.readString();
        this.floor = in.readString();
        this.supervisor = in.readString();
        this.number = in.readString();
        this.type = in.readString();
        this.photo = in.readString();
        this.svPhoto = in.readString();
        this.nip = in.readString();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
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
