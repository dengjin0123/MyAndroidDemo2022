package com.xiangke.bean;

import java.util.List;

public class Event<T> {
    private String code;
    private T data;
    private List<T> dataList;

    public Event(String code) {
        this.code = code;
    }

    public Event(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public Event(String code, List<T> dataList) {
        this.code = code;
        this.dataList = dataList;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
