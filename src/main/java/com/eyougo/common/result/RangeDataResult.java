package com.eyougo.common.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mei on 04/06/2017.
 */
public class RangeDataResult<T> extends DataResult<ArrayList<T>>{

    private Pager pager;

    public static RangeDataResult success(List data, Pager pager){
        return new RangeDataResult(true, data, pager);
    }

    public static RangeDataResult success(String message, List data, Pager pager) {
        return new RangeDataResult(true, message, data, pager);
    }

    public static RangeDataResult failed(String message) {
        return new RangeDataResult(false, message);
    }

    public static RangeDataResult failed(String message, List<Object> data) {
        return new RangeDataResult(false, message, data);
    }

    protected RangeDataResult(boolean success, String message) {
        super(success, message);
    }

    protected RangeDataResult(boolean success, List<T> list, Pager pager) {
        super(success, new ArrayList(list));
        this.pager = pager;
    }

    protected RangeDataResult(boolean success, String message, List<T> list, Pager pager) {
        super(success, message, new ArrayList(list));
        this.pager = pager;
    }

    protected RangeDataResult(boolean success, String message, List<T> list) {
        super(success, message, new ArrayList(list));
        this.pager = new Pager();
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
