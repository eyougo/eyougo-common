package com.eyougo.common.result;

import java.io.Serializable;

/**
 * Created by mei on 11/05/2017.
 */
public class DataResult<T extends Serializable> extends BooleanResult{

    private static final long serialVersionUID = 6868957402462638398L;

    private T data;

    public static DataResult success(Serializable data){
        return new DataResult(true, data);
    }

    public static DataResult success(String message, Serializable data) {
        return new DataResult(true, message, data);
    }

    public static DataResult failed(String message) {
        return new DataResult(false, message);
    }

    public static DataResult failed(String message, Serializable data) {
        return new DataResult(false, message, data);
    }

    protected DataResult(boolean success, String message) {
        super(success, message);
    }

    protected DataResult(boolean success, T data) {
        super(success);
        this.data = data;
    }

    protected DataResult(boolean success, String message, T data) {
        super(success, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DataResult{" +
                "data=" + data +
                "} " + super.toString();
    }
}
