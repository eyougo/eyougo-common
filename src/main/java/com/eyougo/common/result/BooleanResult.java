package com.eyougo.common.result;

import java.io.Serializable;

/**
 * Created by mei on 11/05/2017.
 */
public class BooleanResult implements Serializable {

    private static final long serialVersionUID = 6630847245700759148L;

    private boolean success;
    private String message = "";

    public static BooleanResult success(){
        return new BooleanResult(true);
    }

    public static BooleanResult success(String message){
        return new BooleanResult(true, message);
    }

    public static BooleanResult failed(String message){
        return new BooleanResult(false, message);
    }

    protected BooleanResult(boolean success) {
        this.success = success;
    }

    protected BooleanResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BooleanResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
