package org.d3ifcool.cubeacon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String username;
    private String password;
    private int userPosition;

    public User(String username, String password, int userPosition) {
        this.username = username;
        this.password = password;
        this.userPosition = userPosition;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(int userPosition) {
        this.userPosition = userPosition;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeInt(this.userPosition);
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.userPosition = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
