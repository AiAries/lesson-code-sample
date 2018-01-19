package oo.aries.com.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aries on 2017/4/10.
 */

public class Student implements Parcelable{
    String name;

    private Student(Parcel in) {
        name = in.readString();
    }

    public Student(String name) {
        this.name = name;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
