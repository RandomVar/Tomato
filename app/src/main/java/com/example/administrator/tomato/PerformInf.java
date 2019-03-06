package com.example.administrator.tomato;

import android.os.Parcel;
import android.os.Parcelable;

public class PerformInf implements Parcelable {
    private int mId; //id in database
    private String mName; // file name
    private int mLength; // length of recording in seconds

    public PerformInf()
    {
    }

    public PerformInf(Parcel in) {
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 序列化过程：必须按成员变量声明的顺序进行封装
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mLength);
    }

    // 反序列过程：必须实现Parcelable.Creator接口，并且对象名必须为CREATOR
    // 读取Parcel里面数据时必须按照成员变量声明的顺序，Parcel数据来源上面writeToParcel方法，读出来的数据供逻辑层使用
    public static final Parcelable.Creator<TaskInf> CREATOR = new Parcelable.Creator<TaskInf>() {
        public TaskInf createFromParcel(Parcel in) {
            return new TaskInf(in);
        }

        public TaskInf[] newArray(int size) {
            return new TaskInf[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
}
