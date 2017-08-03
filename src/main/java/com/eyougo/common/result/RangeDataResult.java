package com.eyougo.common.result;

import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by mei on 04/06/2017.
 */
public class RangeDataResult<T> extends DataResult<ArrayList<T>> {

    private Pager pager;

    public static RangeDataResult success(List data, Pager pager){
        return new RangeDataResult(true, data, pager);
    }

    public static RangeDataResult success(List data, Pager pager, String code, Object... args) {
        return new RangeDataResult(true, data, pager, code, args);
    }

    public static RangeDataResult failed(String code, Object... args) {
        return new RangeDataResult(false, code, args);
    }

    public static RangeDataResult failed(List<Object> data, String code, Object... args) {
        return new RangeDataResult(false,  data, code, args);
    }

    protected RangeDataResult(boolean success, String code, Object... args) {
        super(success, code, args);
    }

    protected RangeDataResult(boolean success, List<T> list, Pager pager) {
        super(success, new ArrayList(list));
        this.pager = pager;
    }

    protected RangeDataResult(boolean success, List<T> list, Pager pager, String code, Object... args) {
        super(success, new ArrayList(list), code, args);
        this.pager = pager;
    }

    protected RangeDataResult(boolean success, List<T> list, String code, Object... args) {
        super(success, new ArrayList(list), code, args);
        this.pager = new Pager();
    }

    @Override
    public RangeDataResult localizeMessage(MessageSource messageSource, Locale locale) {
        super.localizeMessage(messageSource, locale);
        return this;
    }

    public Pager getPager() {
        return pager;
    }
}
