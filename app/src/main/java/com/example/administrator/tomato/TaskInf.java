package com.example.administrator.tomato;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskInf implements Parcelable {
    private String mName; // file name
    private int mId; //id in database
    private int mLength; // length of recording in seconds

    public TaskInf()
    {
    }

    public TaskInf(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mLength = in.readInt();
    }

    public int getLength() {
        return mLength;
    }

    public void setLength(int length) {
        mLength = length;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


    public static final Parcelable.Creator<TaskInf> CREATOR = new Parcelable.Creator<TaskInf>() {
        public TaskInf createFromParcel(Parcel in) {
            return new TaskInf(in);
        }

        public TaskInf[] newArray(int size) {
            return new TaskInf[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mLength);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}