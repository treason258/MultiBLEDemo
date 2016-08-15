// IOnNewBookArrivedListener.aidl
package com.mjiayou.trecore.test.aidl;

// Declare any non-default types here with import statements
import com.mjiayou.trecore.test.aidl.Book;

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book book);
}
