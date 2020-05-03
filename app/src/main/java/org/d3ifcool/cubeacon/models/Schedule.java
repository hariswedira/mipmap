package org.d3ifcool.cubeacon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule implements Parcelable {
    String title;
    String time;
    String lecture;
    int isGoing;

    public Schedule(String title, String time, String lecture, int isGoing) {
        this.title = title;
        this.time = time;
        this.lecture = lecture;
        this.isGoing = isGoing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public int getIsGoing() {
        return isGoing;
    }

    public void setIsGoing(int isGoing) {
        this.isGoing = isGoing;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.time);
        dest.writeString(this.lecture);
        dest.writeInt(this.isGoing);
    }

    protected Schedule(Parcel in) {
        this.title = in.readString();
        this.time = in.readString();
        this.lecture = in.readString();
        this.isGoing = in.readInt();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel source) {
            return new Schedule(source);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}
