package com.suto.s4anext.bean;

import java.util.List;

public class SerieObj {
    private int ts;

    private int valueNumber;
    private double[] values;

    public void setValueNumber(int num){
        this.valueNumber = num;
    }
    public void addValues(int ts, double[] values) throws Exception {
        if(values.length!=this.valueNumber){
            throw new Exception("Value size wrong!");
        }
        this.values = values;
    }
}
