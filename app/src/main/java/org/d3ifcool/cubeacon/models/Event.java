package org.d3ifcool.cubeacon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private int poster;
    private String title;
    private String content;
    private String room;
    private String date;

    public Event() {

    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.room);
        dest.writeString(this.date);
    }

    protected Event(Parcel in) {
        this.poster = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.room = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
