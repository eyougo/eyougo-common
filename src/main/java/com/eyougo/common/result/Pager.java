package com.eyougo.common.result;

import java.io.Serializable;

/**
 * Created by mei on 12/06/2017.
 */
public class Pager implements Serializable{

    private static final long serialVersionUID = -3202063997307954163L;

    private int previousOffset = -1;
    private int nextOffset = -1;
    private int total = 0;

    public Pager() {
    }

    public Pager(int previousOffset, int nextOffset, int total) {
        this.previousOffset = previousOffset;
        this.nextOffset = nextOffset;
        this.total = total;
    }

    public int getPreviousOffset() {
        return previousOffset < 0 ? -1 : previousOffset;
    }

    public void setPreviousOffset(int previousOffset) {
        this.previousOffset = previousOffset;
    }

    public int getNextOffset() {
        return nextOffset <0 ? -1 :nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
