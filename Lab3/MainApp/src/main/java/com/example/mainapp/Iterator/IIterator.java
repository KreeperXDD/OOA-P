package com.example.mainapp.Iterator;

public interface IIterator <T>{
    boolean HasNext();
    T Next();
    void Reset();
}
