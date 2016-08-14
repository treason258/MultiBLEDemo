// IBookManager.aidl
package com.mjiayou.trecore.test.aidl;

import com.mjiayou.trecore.test.aidl.Book;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
