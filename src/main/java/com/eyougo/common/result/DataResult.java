package com.eyougo.common.result;

import org.springframework.context.MessageSource;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by mei on 11/05/2017.
 */
public class DataResult<T extends Serializable> extends BooleanResult {

    private static final long serialVersionUID = 6868957402462638398L;

    private T data;

    public static DataResult success(Serializable data){
        return new DataResult(true, data);
    }

    public static DataResult success(Serializable data, String code, Object... args) {
        return new DataResult(true, data, code, args);
    }

    public static DataResult failed(String code, Object... args) {
        return new DataResult(false, code, args);
    }

    public static DataResult failed(Serializable data, String code, Object... args) {
        return new DataResult(false, data, code, args);
    }

    protected DataResult(boolean success, String code, Object... args) {
        super(success, code, args);
    }

    protected DataResult(boolean success, T data) {
        super(success);
        this.data = data;
    }

    protected DataResult(boolean success, T data, String code, Object... args) {
        super(success, code, args);
        this.data = data;
    }

    @Override
    public DataResult localizeMessage(MessageSource messageSource, Locale locale) {
        super.localizeMessage(messageSource, locale);
        return this;
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
