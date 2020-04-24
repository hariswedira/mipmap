package org.d3ifcool.cubeacon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private String poster;
    private String title;
    private String content;
    private String room;
    private String date;
    private String oragnizer;

    public Event() {

    }

    public Event(String poster, String title, String content, String room, String date, String oragnizer) {
        this.poster = poster;
        this.title = title;
        this.content = content;
        this.room = room;
        this.date = date;
        this.oragnizer = oragnizer;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
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

    public String getOragnizer() {
        return oragnizer;
    }

    public void setOragnizer(String oragnizer) {
        this.oragnizer = oragnizer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.room);
        dest.writeString(this.date);
        dest.writeString(this.oragnizer);
    }

    protected Event(Parcel in) {
        this.poster = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.room = in.readString();
        this.date = in.readString();
        this.oragnizer = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
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
