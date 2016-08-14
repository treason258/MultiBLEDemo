package com.mjiayou.trecore.test.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by treason on 16/8/14.
 */
public class Book implements Parcelable {

    private int bookId;
    private String bookName;

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }
}
