package com.cx.gal.tickeTooli.logExtractor.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Galn on 10/18/2018.
 */

public class Ret {
    List<String> lines = new ArrayList<>();
    Date firstDate;


    public Ret() {
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }
}

