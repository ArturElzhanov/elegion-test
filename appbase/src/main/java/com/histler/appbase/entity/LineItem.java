package com.histler.appbase.entity;

import java.io.Serializable;


public class LineItem<T> implements Serializable {
    public T data;
    public boolean isHeader;

    public LineItem(T data, boolean isHeader) {
        this.data = data;
        this.isHeader = isHeader;
    }
}
