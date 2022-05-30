package com.cosmas.moniger.utils;

import java.util.Calendar;

public class SimpleDate {
    public int day, month, year;

    public SimpleDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public SimpleDate()
    {
        this(Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.YEAR));
    }
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return DateTextFormatter.formatText(new SimpleDate(getDay(), getMonth(), getYear()), "/");
    }
}
