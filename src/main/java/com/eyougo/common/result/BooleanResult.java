package com.eyougo.common.result;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by mei on 11/05/2017.
 */
public class BooleanResult implements Serializable {

    private static final long serialVersionUID = 6630847245700759148L;

    private boolean success;
    private String code;
    private Object[] args;
    private String message = "";

    public static BooleanResult success(){
        return new BooleanResult(true);
    }

    public static BooleanResult success(String code, Object... args){
        return new BooleanResult(true, code, args);
    }

    public static BooleanResult failed(String code, Object... args){
        return new BooleanResult(false, code, args);
    }

    protected BooleanResult(boolean success) {
        this.success = success;
    }

    protected BooleanResult(boolean success, String code) {
        this.success = success;
        this.code = code;
    }

    protected BooleanResult(boolean success, String code, Object[] args) {
        this.success = success;
        this.code = code;
        this.args = args;
    }

    public BooleanResult localizeMessage(MessageSource messageSource, Locale locale){
        this.message = messageSource.getMessage(code, args, code, locale);
        return this;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return StringUtils.isEmpty(message) ? code : message;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "BooleanResult{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", args=" + Arrays.toString(args) +
                ", message='" + message + '\'' +
                '}';
    }
}
