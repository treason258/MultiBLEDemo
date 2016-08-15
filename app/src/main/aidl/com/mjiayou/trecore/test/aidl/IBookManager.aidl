// IBookManager.aidl
package com.mjiayou.trecore.test.aidl;

// Declare any non-default types here with import statements
import com.mjiayou.trecore.test.aidl.Book;
import com.mjiayou.trecore.test.aidl.IOnNewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();

    void addBook(in Book book);

    void registerListener(IOnNewBookArrivedListener listener);

    void unregisterListener(IOnNewBookArrivedListener listener);
}
